package ir.alimatin.memorial.simpleCamera.activities

import android.app.Activity
import android.content.Intent
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.*
import android.widget.RelativeLayout
import com.aigestudio.wheelpicker.WheelPicker
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.*
import ir.alimatin.memorial.BuildConfig
import ir.alimatin.memorial.R
import ir.alimatin.memorial.simpleCamera.activities.SettingsActivity
import ir.alimatin.memorial.simpleCamera.activities.SimpleActivity
import ir.alimatin.memorial.simpleCamera.extensions.config
import ir.alimatin.memorial.simpleCamera.helpers.*
import ir.alimatin.memorial.simpleCamera.implementations.MyCameraImpl
import ir.alimatin.memorial.simpleCamera.interfaces.MyPreview
import ir.alimatin.memorial.simpleCamera.views.CameraPreview
import ir.alimatin.memorial.simpleCamera.views.FocusCircleView
import kotlinx.android.synthetic.main.activity_camera.*


class CameraActivity : SimpleActivity(), PhotoProcessor.MediaSavedListener {
    private val FADE_DELAY = 5000L
    private val CAPTURE_ANIMATION_DURATION = 100L

    lateinit var mTimerHandler: Handler
    private lateinit var mOrientationEventListener: OrientationEventListener
    private lateinit var mFocusCircleView: FocusCircleView
    private lateinit var mFadeHandler: Handler
    private lateinit var mCameraImpl: MyCameraImpl

    private var mPreview: MyPreview? = null
    private var mPreviewUri: Uri? = null
    private var mIsInPhotoMode = false
    private var mIsCameraAvailable = false
    private var mIsVideoCaptureIntent = false
    private var mIsHardwareShutterHandled = false
    private var mCurrVideoRecTimer = 0
    var mLastHandledOrientation = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        useDynamicTheme = false
        super.onCreate(savedInstanceState)
        appLaunched(BuildConfig.APPLICATION_ID)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        initVariables()
        tryInitCamera()
        supportActionBar?.hide()
        setupOrientationEventListener()

        /*//Get the widgets reference from XML layout
       var np: NumberPicker = findViewById(R.id.np);


       //Initializing a new string array with elements
       val values = arrayOf("Red", "Green")

       //Populate NumberPicker values from String array values
       //Set the minimum value of NumberPicker
       np.minValue = 0; //from array first value
       //Specify the maximum value/number of NumberPicker
       np.maxValue = values.size - 1; //to array last value

       //Specify the NumberPicker data source as array elements
       np.displayedValues = values;
       //Gets whether the selector wheel wraps when reaching the min/max value.
       np.wrapSelectorWheel = true;

       //Set a value change listener for NumberPicker
       np.setOnValueChangedListener { picker, oldVal, newVal -> //Display the newly selected value from picker
           Toast.makeText(this, "" + values[newVal], Toast.LENGTH_SHORT).show()
       }*/
        wheelPicker.setOnWheelChangeListener(object : WheelPicker.OnWheelChangeListener {
            override fun onWheelScrolled(offset: Int) {
            }

            override fun onWheelSelected(position: Int) {
                handleTogglePhotoVideo(position)
            }

            override fun onWheelScrollStateChanged(state: Int) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (hasStorageAndCameraPermissions()) {
            mPreview?.onResumed()
            resumeCameraItems()
            setupPreviewImage(mIsInPhotoMode)
            scheduleFadeOut()
            mFocusCircleView.setStrokeColor(getAdjustedPrimaryColor())

            if (mIsVideoCaptureIntent && mIsInPhotoMode) {
                handleTogglePhotoVideo(wheelPicker.currentItemPosition)
                checkButtons()
            }
            toggleBottomButtons(false)
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        if (hasStorageAndCameraPermissions()) {
            mOrientationEventListener.enable()
        }
    }

    override fun onPause() {
        super.onPause()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        if (!hasStorageAndCameraPermissions() || isAskingPermissions) {
            return
        }

        mFadeHandler.removeCallbacksAndMessages(null)

        hideTimer()
        mOrientationEventListener.disable()

        if (mPreview?.getCameraState() == STATE_PICTURE_TAKEN) {
            toast(R.string.photo_not_saved)
        }
        mPreview?.onPaused()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPreview = null
    }

    private fun initVariables() {
        mIsInPhotoMode = config.initPhotoMode
        mIsCameraAvailable = false
        mIsVideoCaptureIntent = false
        mIsHardwareShutterHandled = false
        mCurrVideoRecTimer = 0
        mLastHandledOrientation = 0
        mCameraImpl = MyCameraImpl(applicationContext)

        if (config.alwaysOpenBackCamera) {
            config.lastUsedCamera = mCameraImpl.getBackCameraId().toString()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_CAMERA && !mIsHardwareShutterHandled) {
            mIsHardwareShutterHandled = true
            shutterPressed()
            true
        } else if (!mIsHardwareShutterHandled && config.volumeButtonsAsShutter && (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            mIsHardwareShutterHandled = true
            shutterPressed()
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_CAMERA || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            mIsHardwareShutterHandled = false
        }
        return super.onKeyUp(keyCode, event)
    }

    private fun hideIntentButtons() {
        //toggle_photo_video.beGone()
        settings.beGone()
        last_photo_video_preview.beGone()
    }

    private fun tryInitCamera() {
        handlePermission(PERMISSION_CAMERA) {
            if (it) {
                handlePermission(PERMISSION_WRITE_STORAGE) {
                    if (it) {
                        initializeCamera()
                    } else {
                        toast(R.string.no_storage_permissions)
                        finish()
                    }
                }
            } else {
                toast(R.string.no_camera_permissions)
                finish()
            }
        }
    }

    private fun isImageCaptureIntent() = intent?.action == MediaStore.ACTION_IMAGE_CAPTURE || intent?.action == MediaStore.ACTION_IMAGE_CAPTURE_SECURE

    private fun checkImageCaptureIntent() {
        if (isImageCaptureIntent()) {
            hideIntentButtons()
            val output = intent.extras?.get(MediaStore.EXTRA_OUTPUT)
            if (output != null && output is Uri) {
                mPreview?.setTargetUri(output)
            }
        }
    }

    private fun checkVideoCaptureIntent() {
        if (intent?.action == MediaStore.ACTION_VIDEO_CAPTURE) {
            mIsVideoCaptureIntent = true
            mIsInPhotoMode = false
            hideIntentButtons()
            shutter.setImageResource(R.drawable.ic_video_rec)
        }
    }

    private fun initializeCamera() {
        setContentView(R.layout.activity_camera)
        initButtons()

        (btn_holder.layoutParams as RelativeLayout.LayoutParams).setMargins(0, 0, 0, (navigationBarHeight + resources.getDimension(R.dimen.activity_margin)).toInt())

        checkVideoCaptureIntent()
        mPreview = CameraPreview(this, camera_texture_view, mIsInPhotoMode)
        view_holder.addView(mPreview as ViewGroup)
        checkImageCaptureIntent()
        mPreview?.setIsImageCaptureIntent(isImageCaptureIntent())

        val imageDrawable = if (config.lastUsedCamera == mCameraImpl.getBackCameraId().toString()) R.drawable.ic_camera_flip else R.drawable.ic_camera_flip
        toggle_camera.setImageResource(imageDrawable)

        mFocusCircleView = FocusCircleView(applicationContext)
        view_holder.addView(mFocusCircleView)

        mTimerHandler = Handler()
        mFadeHandler = Handler()
        setupPreviewImage(true)

        val initialFlashlightState = if (config.turnFlashOffAtStartup) FLASH_OFF else config.flashlightState
        mPreview!!.setFlashlightState(initialFlashlightState)
        updateFlashlightState(initialFlashlightState)
    }

    private fun initButtons() {
        toggle_camera.setOnClickListener { toggleCamera() }
        last_photo_video_preview.setOnClickListener { showLastMediaPreview() }
        toggle_flash.setOnClickListener { toggleFlash() }
        shutter.setOnClickListener { shutterPressed() }
        settings.setOnClickListener { launchSettings() }
        //toggle_photo_video.setOnClickListener { handleTogglePhotoVideo() }
        change_resolution.setOnClickListener { mPreview?.showChangeResolutionDialog() }
    }

    private fun toggleCamera() {
        if (checkCameraAvailable()) {
            mPreview!!.toggleFrontBackCamera()
        }
    }

    private fun showLastMediaPreview() {
        if (mPreviewUri != null) {
            val path = applicationContext.getRealPathFromURI(mPreviewUri!!)
                    ?: mPreviewUri!!.toString()
            openPathIntent(path, false, BuildConfig.APPLICATION_ID)
        }
    }

    private fun toggleFlash() {
        if (checkCameraAvailable()) {
            mPreview?.toggleFlashlight()
        }
    }

    fun updateFlashlightState(state: Int) {
        config.flashlightState = state
        val flashDrawable = when (state) {
            FLASH_OFF -> R.drawable.ic_flash_off_vector
            FLASH_ON -> R.drawable.ic_flash_off_vector
            else -> R.drawable.ic_flash_on
        }
        toggle_flash.setImageResource(flashDrawable)
    }

    fun updateCameraIcon(isUsingFrontCamera: Boolean) {
        toggle_camera.setImageResource(if (isUsingFrontCamera) R.drawable.ic_camera_flip else R.drawable.ic_camera_flip)
    }

    private fun shutterPressed() {
        if (checkCameraAvailable()) {
            handleShutter()
        }
    }

    private fun handleShutter() {
        if (mIsInPhotoMode) {
            toggleBottomButtons(true)
            mPreview?.tryTakePicture()
            capture_black_screen.animate().alpha(0.8f).setDuration(CAPTURE_ANIMATION_DURATION).withEndAction {
                capture_black_screen.animate().alpha(0f).setDuration(CAPTURE_ANIMATION_DURATION).start()
            }.start()
        } else {
            mPreview?.toggleRecording()
        }
    }

    fun toggleBottomButtons(hide: Boolean) {
        runOnUiThread {
            val alpha = if (hide) 0f else 1f
            shutter.animate().alpha(alpha).start()
            toggle_camera.animate().alpha(alpha).start()
            toggle_flash.animate().alpha(alpha).start()

            shutter.isClickable = !hide
            toggle_camera.isClickable = !hide
            toggle_flash.isClickable = !hide
        }
    }

    private fun launchSettings() {
        if (settings.alpha == 1f) {
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
        } else {
            fadeInButtons()
        }
    }

    private fun handleTogglePhotoVideo(position: Int) {
        handlePermission(PERMISSION_RECORD_AUDIO) {
            if (it) {
                togglePhotoVideo(position)
            } else {
                toast(R.string.no_audio_permissions)
                if (mIsVideoCaptureIntent) {
                    finish()
                }
            }
        }
    }

    private fun togglePhotoVideo(position: Int) {
        if (!checkCameraAvailable()) {
            return
        }

        if (mIsVideoCaptureIntent) {
            mPreview?.tryInitVideoMode()
        }

        mPreview?.setFlashlightState(FLASH_OFF)
        hideTimer()
        mIsInPhotoMode = position == 0
        config.initPhotoMode = mIsInPhotoMode
        showToggleCameraIfNeeded()
        checkButtons()
        toggleBottomButtons(false)
    }

    private fun checkButtons() {
        if (mIsInPhotoMode) {
            initPhotoMode()
        } else {
            tryInitVideoMode()
        }
    }

    private fun initPhotoMode() {
        //toggle_photo_video.setImageResource(R.drawable.ic_video_vector)
        shutter.setImageResource(R.drawable.ic_shutter)
        mPreview?.initPhotoMode()
        setupPreviewImage(true)
    }

    private fun tryInitVideoMode() {
        if (mPreview?.initVideoMode() == true) {
            initVideoButtons()
        } else {
            if (!mIsVideoCaptureIntent) {
                toast(R.string.video_mode_error)
            }
        }
    }

    private fun initVideoButtons() {
        //toggle_photo_video.setImageResource(R.drawable.ic_camera_vector)
        showToggleCameraIfNeeded()
        shutter.setImageResource(R.drawable.ic_video_rec)
        setupPreviewImage(false)
        mPreview?.checkFlashlight()
    }

    /* private fun setupPreviewImagesList(isPhoto: Boolean): ArrayList<PhotoClass> {
         val listPhotoPhone: ArrayList<PhotoClass> = ArrayList()
         val cursor: Cursor?
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
             // Codigo version de api 29 en adelante
             cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Images.Media._ID),
                     null,
                     null,
                     MediaStore.Images.Media.DATE_TAKEN + " DESC"
             )
             if (null == cursor) {
                 return listPhotoPhone
             }
             if (cursor.moveToFirst()) {
                 do {
                     val photoUrl = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))).toString()
                     val photo = PhotoClass(photoUrl)
                     listPhotoPhone.add(photo)
                 } while (cursor.moveToNext())
             }
         } else {
             cursor = contentResolver.query(
                     MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                     null,
                     null,
                     null,
                     MediaStore.Images.Media.DATA + " DESC"
             )
             if (null == cursor) {
                 return listPhotoPhone
             }
             if (cursor.moveToFirst()) {
                 do {
                     val fullPath: String = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                     val photo = PhotoClass(fullPath)
                     listPhotoPhone.add(photo)
                 } while (cursor.moveToNext())
             }
         }
         cursor.close()
         return listPhotoPhone
     }

    private class PhotoClass(val fullPath: String)
    */
    private fun setupPreviewImage(isPhoto: Boolean) {
        val uri = if (isPhoto)
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        else MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val lastMediaId = getLatestMediaId(uri)
        if (lastMediaId == 0L) {
            return
        }

        mPreviewUri = Uri.withAppendedPath(uri, lastMediaId.toString())

        runOnUiThread {
            if (!isDestroyed) {
                val options = RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)

                Glide.with(this)
                        .load(mPreviewUri)
                        .apply(options)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(last_photo_video_preview)
            }
        }
    }

    private fun scheduleFadeOut() {
        if (!config.keepSettingsVisible) {
            mFadeHandler.postDelayed({
                fadeOutButtons()
            }, FADE_DELAY)
        }
    }

    private fun fadeOutButtons() {
        fadeAnim(settings, .5f)
        //fadeAnim(toggle_photo_video, .0f)
        fadeAnim(change_resolution, .0f)
        fadeAnim(last_photo_video_preview, .0f)
    }

    private fun fadeInButtons() {
        fadeAnim(settings, 1f)
        //fadeAnim(toggle_photo_video, 1f)
        fadeAnim(change_resolution, 1f)
        fadeAnim(last_photo_video_preview, 1f)
        scheduleFadeOut()
    }

    private fun fadeAnim(view: View, value: Float) {
        view.animate().alpha(value).start()
        view.isClickable = value != .0f
    }

    private fun hideNavigationBarIcons() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE
    }

    private fun showTimer() {
        video_rec_curr_timer.beVisible()
        setupTimer()
    }

    private fun hideTimer() {
        video_rec_curr_timer.text = 0.getFormattedDuration()
        video_rec_curr_timer.beGone()
        mCurrVideoRecTimer = 0
        mTimerHandler.removeCallbacksAndMessages(null)
    }

    private fun setupTimer() {
        runOnUiThread(object : Runnable {
            override fun run() {
                video_rec_curr_timer.text = mCurrVideoRecTimer++.getFormattedDuration()
                mTimerHandler.postDelayed(this, 1000L)
            }
        })
    }

    private fun resumeCameraItems() {
        showToggleCameraIfNeeded()
        hideNavigationBarIcons()

        if (!mIsInPhotoMode) {
            initVideoButtons()
        }
    }

    private fun showToggleCameraIfNeeded() {
        toggle_camera?.beInvisibleIf(mCameraImpl.getCountOfCameras() ?: 1 <= 1)
    }

    private fun hasStorageAndCameraPermissions() = hasPermission(PERMISSION_WRITE_STORAGE) && hasPermission(PERMISSION_CAMERA)

    private fun setupOrientationEventListener() {
        mOrientationEventListener = object : OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            override fun onOrientationChanged(orientation: Int) {
                if (isDestroyed) {
                    mOrientationEventListener.disable()
                    return
                }

                val currOrient = when (orientation) {
                    in 75..134 -> ORIENT_LANDSCAPE_RIGHT
                    in 225..289 -> ORIENT_LANDSCAPE_LEFT
                    else -> ORIENT_PORTRAIT
                }

                if (currOrient != mLastHandledOrientation) {
                    val degrees = when (currOrient) {
                        ORIENT_LANDSCAPE_LEFT -> 90
                        ORIENT_LANDSCAPE_RIGHT -> -90
                        else -> 0
                    }

                    animateViews(degrees)
                    mLastHandledOrientation = currOrient
                }
            }
        }
    }

    private fun animateViews(degrees: Int) {
        val views = arrayOf<View>(toggle_camera, toggle_flash, change_resolution, shutter, settings, last_photo_video_preview)
        for (view in views) {
            rotate(view, degrees)
        }
    }

    private fun rotate(view: View, degrees: Int) = view.animate().rotation(degrees.toFloat()).start()

    private fun checkCameraAvailable(): Boolean {
        if (!mIsCameraAvailable) {
            toast(R.string.camera_unavailable)
        }
        return mIsCameraAvailable
    }

    fun setFlashAvailable(available: Boolean) {
        if (available) {
            toggle_flash.beVisible()
        } else {
            toggle_flash.beInvisible()
            toggle_flash.setImageResource(R.drawable.ic_flash_off_vector)
            mPreview?.setFlashlightState(FLASH_OFF)
        }
    }

    fun setIsCameraAvailable(available: Boolean) {
        mIsCameraAvailable = available
    }

    fun setRecordingState(isRecording: Boolean) {
        runOnUiThread {
            if (isRecording) {
                shutter.setImageResource(R.drawable.ic_link)
                toggle_camera.beInvisible()
                showTimer()
            } else {
                shutter.setImageResource(R.drawable.ic_video_rec)
                showToggleCameraIfNeeded()
                hideTimer()
            }
        }
    }

    fun videoSaved(uri: Uri) {
        setupPreviewImage(false)
        if (mIsVideoCaptureIntent) {
            Intent().apply {
                data = uri
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                setResult(Activity.RESULT_OK, this)
            }
            finish()
        }
    }

    fun drawFocusCircle(x: Float, y: Float) = mFocusCircleView.drawFocusCircle(x, y)

    override fun mediaSaved(path: String) {
        rescanPaths(arrayListOf(path)) {
            setupPreviewImage(true)
            Intent(BROADCAST_REFRESH_MEDIA).apply {
                putExtra(REFRESH_PATH, path)
                `package` = "com.simplemobiletools.gallery"
                sendBroadcast(this)
            }
        }

        if (isImageCaptureIntent()) {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}

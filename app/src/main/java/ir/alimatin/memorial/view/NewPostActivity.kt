package ir.alimatin.memorial.view

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.imgpicker.ButtonType
import ir.alimatin.memorial.R
import ir.alimatin.memorial.common.DialogHandler
import ir.alimatin.memorial.common.DialogHandler.OnMyDialogResult
import ir.alimatin.memorial.databinding.ActivityNewPostBinding
import ir.alimatin.memorial.utilities.AppUtilities.showToast
import ir.alimatin.memorial.utilities.FilePath
import ir.alimatin.memorial.utilities.ProgressRequestBody
import ir.alimatin.memorial.utilities.SharedPrefs
import ir.alimatin.memorial.viewmodel.PostViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "NewPostActivity"

class NewPostActivity : AppCompatActivity(), BottomSheetImagePicker.OnImagesSelectedListener,
    LocationListener {
    private lateinit var postViewModel: PostViewModel
    private lateinit var contInst: Context
    private lateinit var viewDataBinding: ActivityNewPostBinding
    private var imageFile: String = ""
    private var sendLater = ""
    var gpsStatus: Boolean = false
    var latitude = ""
    var longitude = ""
    lateinit var sharedPrefs: SharedPrefs

    private var locationManager: LocationManager? = null
    private lateinit var listener: LocationListener

    init {
        sendLater =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) + "T00:00:00+00:00"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_post)

        contInst = this
        sharedPrefs = SharedPrefs(contInst)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        gpsStatus = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        val criteria = Criteria()
        val provider = locationManager!!.getBestProvider(criteria, false)
        val location = provider?.let { locationManager!!.getLastKnownLocation(it) }

        if (location != null) {
            println("Provider $provider has been selected.");
            onLocationChanged(location);
        }

/*

        listener = object : LocationListener {
            @SuppressLint("SetTextI18n")
            override fun onLocationChanged(location: Location) {
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()
                viewDataBinding.tvLocation.text = "$latitude $longitude"

                Handler().postDelayed({
                    try {
                        val geocoder = Geocoder(this@NewPostActivity, Locale.ENGLISH)
                        val addresses: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val address = addresses[0].getAddressLine(0)
                        Toast.makeText(this@NewPostActivity, address, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }, 100)

            }

            override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {
                //
            }

            override fun onProviderEnabled(s: String) {
                //
            }

            override fun onProviderDisabled(s: String) {
                DialogHandler().showDialogLocationSystem(contInst)
            }
        }
*/

        if (gpsStatus) {
            //getLocation()
            LocationServices.getFusedLocationProviderClient(this)
            configure_button()
        } else {
            DialogHandler().showDialogLocationSystem(contInst)
        }

        onClick()
    }

    private fun location(location: Location) {
        val geoCoder = Geocoder(this, Locale.getDefault()) //it is Geocoder

        val builder = StringBuilder()
        try {
            val address = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
            val maxLines = address[0].maxAddressLineIndex
            for (i in 0 until maxLines) {
                val addressStr = address[0].getAddressLine(i)
                builder.append(addressStr)
                builder.append(" ")
            }
            val fnialAddress = builder.toString() //This is the complete address.
            Toast.makeText(this, fnialAddress, Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
        } catch (e: NullPointerException) {
        }
    }

    private fun onClick() {
        viewDataBinding.apply {
            tvAdvancedSetting.setOnClickListener {
                DialogHandler().showDialogAdvancedSetting(contInst)
            }

            ivUser.setOnClickListener {

                BottomSheetImagePicker.Builder(getString(R.string.file_provider))
                    .cameraButton(ButtonType.Button)
                    .galleryButton(ButtonType.Button)
                    .singleSelectTitle(R.string.pick_single)
                    .peekHeight(R.dimen.peekHeight)
                    .requestTag("single")
                    .show(supportFragmentManager)

            }

            tvSendLater.setOnClickListener {
                //DialogHandler().showDialogSetAsDefult(contInst)
                DialogHandler().apply {
                    showDialogDatePicker(contInst)
                    setDialogResult(object : OnMyDialogResult {
                        override fun finish(result: String?) {
                            sendLater = result.toString()
                        }
                    })
                }

            }

            tvShare.setOnClickListener {
                if (imageFile.isNullOrEmpty()) {
                    Toast.makeText(this@NewPostActivity, "choose an image", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (viewDataBinding.etCaption.text.isNullOrEmpty()) {
                    Toast.makeText(this@NewPostActivity, "enter your caption", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                vsShare.displayedChild = 1
                postViewModel.uploadImage(File(imageFile), uploadProgressListener)
                    .observe(this@NewPostActivity, androidx.lifecycle.Observer { uploadData ->
                        if (!uploadData.filename.isNullOrEmpty()) {
                            postViewModel
                                .setPost(
                                    sharedPrefs.getToken(),
                                    uploadData.filename,
                                    viewDataBinding.etCaption.text.toString(),
                                    "",
                                    latitude,
                                    longitude,
                                    if (swComment.isChecked) 1 else 0,
                                    if (swRank.isChecked) 1 else 0,
                                    if (swGhost.isChecked) 1 else 0,
                                    if (swFacebook.isChecked) 1 else 0,
                                    if (swTwitter.isChecked) 1 else 0,
                                    if (swTelegram.isChecked) 1 else 0,
                                    sbPrivacy.progress,
                                    sendLater
                                )
                                .observe(this@NewPostActivity, androidx.lifecycle.Observer { data ->
                                    if (data.status == "201") {
                                        Toast.makeText(
                                            this@NewPostActivity,
                                            resources.getString(R.string.successful),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        finish()
                                    }
                                    vsShare.displayedChild = 0
                                })
                        } else {
                            vsShare.displayedChild = 0
                        }
                    })
            }

            ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private val uploadProgressListener = object :
        ProgressRequestBody.UploadCallbacks {
        override fun onProgressUpdate(percentage: Int) {

            Log.e(TAG, "onUpdating: progress $percentage")
        }

        override fun onError() {
            Log.e(TAG, "onError: progress")
        }

        override fun onFinish() {
            Log.e(TAG, "onError: finish")
        }

    }

    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
        uris.forEach { uri ->
            Log.e(TAG, "onImagesSelected: ${File(uri.path!!).length()}")
            if (File(uri.path!!).length() / 1024 < 1024)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    viewDataBinding.ivUser.visibility = View.VISIBLE
                    Glide.with(this).load(uri)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewDataBinding.ivUser)
                    imageFile = FilePath.getPath(this, uri).toString()
                } else
                    this.showToast(resources.getString(R.string.error_large_image))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            10 -> configure_button()
            else -> {
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(
                this@NewPostActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@NewPostActivity,
                ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                request_permission()
            }
        } else {
            // permission has been granted
            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0f,
                this
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun request_permission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@NewPostActivity,
                ACCESS_COARSE_LOCATION
            )
        ) {
            Snackbar.make(
                findViewById(R.id.root), "Location permission is needed because ...",
                Snackbar.LENGTH_LONG
            )
                .setAction(
                    "retry"
                ) {
                    requestPermissions(
                        arrayOf(
                            ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ), 10
                    )
                }
                .show()
        } else {
            // permission has not been granted yet. Request it directly.
            requestPermissions(
                arrayOf(
                    ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 10
            )
        }
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude.toString()
        longitude = location.longitude.toString()
        viewDataBinding.tvLocation.text = "$latitude $longitude"

        location(location)
    }

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {
        //
    }

    override fun onProviderEnabled(s: String) {
        //
    }

    override fun onProviderDisabled(s: String) {
        DialogHandler().showDialogLocationSystem(contInst)
    }
}
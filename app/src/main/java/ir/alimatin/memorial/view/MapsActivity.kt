package ir.alimatin.memorial.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton
import com.fangxu.allangleexpandablebutton.ButtonData
import com.fangxu.allangleexpandablebutton.ButtonEventListener
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import customView.space.SunSetView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.common.DialogHandler
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_maps.cardView
import kotlinx.android.synthetic.main.activity_new_post.*
import java.util.*
import kotlin.collections.ArrayList

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var contInst: Context
    var gpsStatus: Boolean = false


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        initiFAB()
        locationEnabled()
        contInst = this


        if (gpsStatus) {
            //getLocation()
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            showToast("Location is Enabled")
        } else {
            DialogHandler().showDialogLocationSystem(contInst)
        }
    }

    private fun initiFAB() {
        val buttonDatas: MutableList<ButtonData> = ArrayList()
        val drawable = intArrayOf(
            R.drawable.ic_user2,
            R.drawable.ic_location,
            R.drawable.ic_destination,
            R.drawable.ic_ghost,
            R.drawable.ic_camera,
            R.drawable.ic_mic,
            R.drawable.ic_message
        )
        val color = intArrayOf(R.color.colorPrimaryDark, android.R.color.white, android.R.color.white, android.R.color.white, android.R.color.white, android.R.color.white, android.R.color.white)
        for (i in drawable.indices) {
            val buttonData: ButtonData = if (i == 0) {
                ButtonData.buildIconButton(this, drawable[i], 10f)
            } else {
                ButtonData.buildIconButton(this, drawable[i], 12f)
            }
            buttonData.setBackgroundColorId(this, color[i])
            buttonDatas.add(buttonData)
        }
        fabButton.buttonDatas = buttonDatas
        setListener(fabButton)


    }

    private fun setListener(button: AllAngleExpandableButton) {
        button.setButtonEventListener(object : ButtonEventListener {
            override fun onButtonClicked(index: Int) {
                showToast("clicked index:$index")
                when (index) {
                    1 -> startActivity(Intent(this@MapsActivity, UserActivity::class.java))
                    2 -> return
                    3 -> return
                    4 -> startActivity(Intent(this@MapsActivity, NewPostActivity::class.java))
                    5 -> return
                    else -> return
                }
            }

            override fun onExpand() {
                fabButton.isEnabled = false
            }

            override fun onCollapse() {
                fabButton.isEnabled = true
//                showToast("onCollapse");
            }
        })
        cardView.setOnClickListener {
            startActivity(Intent(this@MapsActivity, ProfileActivity::class.java))
        }
        ivDirect.setOnClickListener {
            startActivity(Intent(this@MapsActivity, DirectActivity::class.java))
        }
        ivNotification.setOnClickListener {
            startActivity(Intent(this@MapsActivity, ActivityActivity::class.java))
        }
        iv_explorer.setOnClickListener {
            startActivity(Intent(this@MapsActivity, ExploreActivity::class.java))
        }
    }

    override fun onBackPressed() {
        if (fabButton.isEnabled) {
            super.onBackPressed()
        }
    }

    private fun showToast(toast: String) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }

    fun startAnim(v: View?) {
        if (v is SunSetView) {
            v.startSunset()
        }
    }

    private fun locationEnabled() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Gerash, Fars-Iran.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Gerash and move the camera
        val location = LatLng(27.667535, 54.142194)
        val markerOptions = MarkerOptions().position(location).title("گراش").icon(BitmapDescriptorFactory.fromResource(
            R.drawable.one_one
        ))
        val markerStart = mMap!!.addMarker(markerOptions)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(location))
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        val circleOptions = CircleOptions()
        circleOptions.center(location)
        circleOptions.radius(3.048)
        circleOptions.strokeColor(Color.TRANSPARENT)
        circleOptions.fillColor(Color.TRANSPARENT)
        mMap!!.addCircle(circleOptions)

        val options = GoogleMapOptions()
        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(true)
                .rotateGesturesEnabled(true)
                .tiltGesturesEnabled(true)

        mMap!!.setOnCameraMoveListener {
            val latLng = mMap!!.cameraPosition.target
            //markerStart.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
            markerStart.position = latLng
            //Toast.makeText(MapsActivity.this,"حرکت",Toast.LENGTH_SHORT).show();
        }
        mMap!!.setOnCameraIdleListener {

        }

        getLocationAccess()
    }


    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
        else
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                mMap.isMyLocationEnabled = true
            }
            else {
                Toast.makeText(this, "User has not granted location access permission", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
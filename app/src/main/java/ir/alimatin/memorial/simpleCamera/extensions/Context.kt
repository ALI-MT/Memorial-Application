package ir.alimatin.memorial.simpleCamera.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import ir.alimatin.memorial.simpleCamera.helpers.Config
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

val Context.config: Config get() = Config.newInstance(applicationContext)

fun Context.getOutputMediaFile(isPhoto: Boolean): String {
    val mediaStorageDir = File(config.savePhotosFolder)

    if (!mediaStorageDir.exists()) {
        if (!mediaStorageDir.mkdirs()) {
            return ""
        }
    }

    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    return if (isPhoto) {
        "${mediaStorageDir.path}/IMG_$timestamp.jpg"
    } else {
        "${mediaStorageDir.path}/VID_$timestamp.mp4"
    }
}

fun Context.hasPermission(permission: String): Boolean {

    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

fun Activity.requestPermissionWithRationale(
    permission: String,
    requestCode: Int,
    rationaleStr: String
) {
    val provideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

//    if (provideRationale) {
//        AlertDialog.Builder(this).apply {
//            setTitle("Permission")
//            setMessage(rationaleStr)
//            setPositiveButton("Ok") { _, _ ->
//                ActivityCompat.requestPermissions(
//                    this@requestPermissionWithRationale,
//                    arrayOf(permission),
//                    requestCode
//                )
//            }
//            create()
//            show()
//        }
//    } else {
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
//    }
}

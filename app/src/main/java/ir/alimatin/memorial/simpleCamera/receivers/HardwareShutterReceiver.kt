package ir.alimatin.memorial.simpleCamera.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ir.alimatin.memorial.simpleCamera.activities.CameraActivity

class HardwareShutterReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Intent(context.applicationContext, CameraActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }
}

package ir.alimatin.memorial.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ir.alimatin.memorial.R
import ir.alimatin.memorial.utilities.SharedPrefs
import kotlinx.coroutines.*


@ExperimentalCoroutinesApi
class SplashActivity : AppCompatActivity() {

    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPrefs = SharedPrefs(this)
        Log.e("token", sharedPrefs.getToken())
        CoroutineScope(Dispatchers.Default).launch {
            delay(2000)
            if (!sharedPrefs.isLogin())
                startActivity(Intent(this@SplashActivity, UserVerifyActivity::class.java))
            else
                startActivity(Intent(this@SplashActivity, MapsActivity::class.java))
            finish()
        }
    }
}
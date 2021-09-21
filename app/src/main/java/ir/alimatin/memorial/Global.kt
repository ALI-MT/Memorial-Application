package ir.alimatin.memorial

import android.app.Application
import android.content.Context

class Global : Application() {

    companion object {
        @Volatile
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
    }
}
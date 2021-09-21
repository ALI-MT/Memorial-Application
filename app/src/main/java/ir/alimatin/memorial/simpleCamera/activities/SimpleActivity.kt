package ir.alimatin.memorial.simpleCamera.activities

import com.simplemobiletools.commons.activities.BaseSimpleActivity
import ir.alimatin.memorial.R

open class SimpleActivity : BaseSimpleActivity() {
    override fun getAppIconIDs() = arrayListOf(
            R.mipmap.ic_launcher
    )

    override fun getAppLauncherName() = getString(R.string.app_name)
}

package ir.alimatin.memorial.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.alimatin.memorial.R
import kotlinx.android.synthetic.main.activity_message.*

class SettingActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        contInst = this
        ivBack.setOnClickListener {
            finish()
        }
    }


}
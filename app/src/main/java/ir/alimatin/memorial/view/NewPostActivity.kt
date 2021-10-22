package ir.alimatin.memorial.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.alimatin.memorial.R
import ir.alimatin.memorial.common.DialogHandler
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_new_post.ivBack

class NewPostActivity : AppCompatActivity() {
    private lateinit var contInst: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
        contInst = this
        onClick()
    }

    private fun onClick() {
        tv_advanced_setting.setOnClickListener{
            DialogHandler().showDialogAdvancedSetting(contInst)
        }

        tvSendLater.setOnClickListener {
            //DialogHandler().showDialogSetAsDefult(contInst)
            DialogHandler().showDialogDatePicker(contInst)
        }

        ivBack.setOnClickListener {
            finish()
        }
    }
}
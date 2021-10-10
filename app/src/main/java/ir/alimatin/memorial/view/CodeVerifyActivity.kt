package ir.alimatin.memorial.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import customView.otpView.OnOtpCompletionListener
import ir.alimatin.memorial.R
import ir.alimatin.memorial.databinding.ActivityCodeVerifyBinding
import ir.alimatin.memorial.utilities.SharedPrefs
import kotlinx.android.synthetic.main.activity_code_verify.*
import kotlinx.android.synthetic.main.activity_user_verify.*

class CodeVerifyActivity : AppCompatActivity(), View.OnClickListener, OnOtpCompletionListener {

    lateinit var viewDataBinding: ActivityCodeVerifyBinding
    lateinit var code: String
    lateinit var token: String
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_code_verify)
        sharedPrefs = SharedPrefs(this)

        intent.extras?.let {
            code = it.getString("code").toString()
            token = it.getString("token").toString()
        }
        setListeners()
    }

    override fun onClick(v: View) {
        if (v == viewDataBinding.btnCodeVerify) {
            if (otp_view.length() < 4) {
                showToast("length < 4")
            } else {
                if (code == otp_view.text.toString()) {
//                    Toast.makeText(this, viewDataBinding.otpView.text, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@CodeVerifyActivity, MapsActivity::class.java))
                    sharedPrefs.saveToken(token)
                    finishAffinity()
                } else
                    showToast(resources.getString(R.string.wrong_code))

            }
        }
    }

    private fun setListeners() {
        with(viewDataBinding) {
            btnCodeVerify.setOnClickListener(this@CodeVerifyActivity)
            otpView.setOtpCompletionListener(this@CodeVerifyActivity)
        }
    }

    override fun onOtpCompleted(otp: String) {
        // do Stuff
//        Toast.makeText(this, viewDataBinding.otpView.text, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(toast: String) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }
}
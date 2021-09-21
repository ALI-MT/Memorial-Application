package ir.alimatin.memorial.view

import android.content.Intent
import android.graphics.PointF.length
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import customView.otpView.OnOtpCompletionListener
import customView.otpView.OtpView
import ir.alimatin.memorial.R
import kotlinx.android.synthetic.main.activity_code_verify.*
import kotlinx.android.synthetic.main.activity_user_verify.*

class CodeVerifyActivity : AppCompatActivity(), View.OnClickListener, OnOtpCompletionListener {
    private var btnValidate: Button? = null
    private var otpView: OtpView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_verify)

        /*btnCodeVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CodeVerifyActivity.this, MapsActivity.class));
            }
        });*/
        initializeUi()
        setListeners()
    }

    override fun onClick(v: View) {
        if (v == btnValidate) {
            if (otp_view.length() < 4){
                showToast("length < 4")
            } else {
                Toast.makeText(this, otpView!!.text, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@CodeVerifyActivity, MapsActivity::class.java))
            }
        }
    }

    private fun initializeUi() {
        otpView = findViewById(R.id.otp_view)
        btnValidate = findViewById(R.id.btn_CodeVerify)
    }

    private fun setListeners() {
        btnValidate!!.setOnClickListener(this)
        otpView!!.setOtpCompletionListener(this)
    }

    override fun onOtpCompleted(otp: String) {
        // do Stuff
        Toast.makeText(this, otpView!!.text, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(toast: String) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }
}
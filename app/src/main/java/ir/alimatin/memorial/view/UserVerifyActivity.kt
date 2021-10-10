package ir.alimatin.memorial.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ir.alimatin.memorial.R
import ir.alimatin.memorial.databinding.ActivityUserVerifyBinding
import ir.alimatin.memorial.viewmodel.UserVerifyActivityViewModel
import kotlinx.android.synthetic.main.activity_user_verify.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


private const val TAG = "UserVerifyActivity"

@ExperimentalCoroutinesApi
class UserVerifyActivity : AppCompatActivity() {
    lateinit var userVerifyActivityViewModel: UserVerifyActivityViewModel
    lateinit var viewDataBinding: ActivityUserVerifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_verify)

        viewDataBinding.btnUserVerify.setOnClickListener {
            if (et_Mobile.length() < 11) {
                showToast("length < 11")
            } else {
                userVerifyActivityViewModel =
                    ViewModelProvider(this).get(UserVerifyActivityViewModel::class.java)

                sendMobile()
            }
        }
    }

    private fun sendMobile() {
        viewDataBinding.vsUserVerify.displayedChild = 1

        userVerifyActivityViewModel.sendMobile(viewDataBinding.etMobile.text.toString())
            .observe(this, Observer { dataUser ->
                Log.e(TAG, "sendMobile: ${dataUser.code}")
                showToast(dataUser.code.toString())
                if (dataUser.status == 201) {
                    val intent = Intent(this@UserVerifyActivity, CodeVerifyActivity::class.java)
                    intent.putExtra("code", dataUser.code)
                    intent.putExtra("token", dataUser._id)
                    startActivity(intent)
                }
                finish()
                viewDataBinding.vsUserVerify.displayedChild = 0
            })
    }

    private fun showToast(toast: String) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }
}

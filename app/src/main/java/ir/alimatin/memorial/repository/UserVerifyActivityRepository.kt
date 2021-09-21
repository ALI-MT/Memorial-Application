package ir.alimatin.memorial.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import ir.alimatin.memorial.Global
import ir.alimatin.memorial.model.UserVerifyModel
import ir.alimatin.memorial.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserVerifyActivityRepository {

    val serviceSetterGetter = MutableLiveData<UserVerifyModel>()

    fun getUserVerifyApiCall(mobile: String): MutableLiveData<UserVerifyModel> {

        val call = RetrofitClient.apiInterface.getUserVerify(mobile)

        call.enqueue(object : Callback<UserVerifyModel> {
            override fun onFailure(call: Call<UserVerifyModel>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
                Toast.makeText(Global.appContext, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<UserVerifyModel>,
                response: Response<UserVerifyModel>
            ) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                val msg = data!!.firstName

                serviceSetterGetter.value = UserVerifyModel(msg)
            }
        })

        return serviceSetterGetter
    }
}
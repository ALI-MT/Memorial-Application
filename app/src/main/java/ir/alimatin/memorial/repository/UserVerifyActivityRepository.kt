package ir.alimatin.memorial.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ir.alimatin.memorial.model.UserVerifyModel
import ir.alimatin.memorial.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserVerifyActivityRepository {

    val serviceSetterGetter = MutableLiveData<UserVerifyModel>()

    fun getUserVerifyApiCall(): MutableLiveData<UserVerifyModel> {

        val call = RetrofitClient.apiInterface.getUserVerify("0989902505074")

        call.enqueue(object: Callback<UserVerifyModel> {
            override fun onFailure(call: Call<UserVerifyModel>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                    call: Call<UserVerifyModel>,
                    response: Response<UserVerifyModel>
            ) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                val msg = data!!.firstName

                //serviceSetterGetter.value = UserVerifyModel(msg)
            }
        })

        return serviceSetterGetter
    }
}
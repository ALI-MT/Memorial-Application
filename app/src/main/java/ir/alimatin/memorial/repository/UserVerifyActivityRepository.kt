package ir.alimatin.memorial.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import ir.alimatin.memorial.Global
import ir.alimatin.memorial.model.UserVerifyModel
import ir.alimatin.memorial.retrofit.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

object UserVerifyActivityRepository {

    val serviceSetterGetter = MutableLiveData<UserVerifyModel>()

    val sendCodeVerifySetterGetter = MutableLiveData<UserVerifyModel>()

    val sendMobile = MutableLiveData<UserVerifyModel>()
    var isReq = false

    fun sendMobileApi(mobile: String): MutableLiveData<UserVerifyModel> {
        if (!isReq) {
            isReq = true
            val call = RetrofitClient.apiInterface.sendMobile(mobile)

            call.enqueue(object : Callback<UserVerifyModel> {
                override fun onFailure(call: Call<UserVerifyModel>, t: Throwable) {
                    Log.v("DEBUG : ", t.message.toString())
                }

                override fun onResponse(
                    call: Call<UserVerifyModel>,
                    response: Response<UserVerifyModel>
                ) {
                    Log.v("DEBUG : ", response.body().toString())

                    response.body().apply {
                        sendMobile.value = UserVerifyModel(
                            this?.status,
                            this?._id,
                            this?.code,
                            this?.dateCreated,
                            this?.__v
                        )
                    }

                }
            })
        }
        return sendMobile
    }


    fun requestBodyBuilder(
        stringMap: java.util.HashMap<String, String> = hashMapOf(),
        imageMap: java.util.HashMap<String, File?> = hashMapOf()
    ): RequestBody {
        val builder: MultipartBody.Builder = MultipartBody.Builder()
        builder.setType(MultipartBody.MIXED)
        stringMap.entries.forEach { (k, v) ->
            builder.addFormDataPart(k, v)
        }
        return builder.build()
    }

//    fun sendMobileApiCall(mobile: String) : Flow<UserVerifyModel?> = flow {
//        val response = RetrofitClient.apiInterface.sendMobile(mobile)
//        if(response.isSuccessful && response.body() != null) {
//            if(response.body() != null){
//                emit(response.body()!!)
//            }
//        }
//    }.flowOn(Dispatchers.IO)
/*

    fun sendMobileApiCall(mobile: String): Flow<MutableLiveData<UserVerifyModel>> {

        val lstMap: HashMap<String, String> = HashMap()
        lstMap["phone"] = mobile
        val call = RetrofitClient.apiInterface.sendMobile(requestBodyBuilder(lstMap))

        call.enqueue(object : Callback<UserVerifyModel> {
            override fun onFailure(call: Call<UserVerifyModel>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
                Toast.makeText(Global.appContext, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<UserVerifyModel>,
                response: Response<UserVerifyModel>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                response.body()?.let {
                    serviceSetterGetter.value =
                        UserVerifyModel(it.status, it._id, it.code, it.dateCreated, it.__v)
                }
            }
        })

        return sendCodeVerifySetterGetter
    }
*/
/*
    fun sendVerifyCodeApiCall(mobile: String): MutableLiveData<UserVerifyModel> {

        val call = RetrofitClient.apiInterface.sendVerifyCode(mobile)

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


//                sendCodeVerifySetterGetter.value = UserVerifyModel(msg)
            }
        })

        return sendCodeVerifySetterGetter
    }*/
}
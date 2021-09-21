package ir.alimatin.memorial.retrofit

import ir.alimatin.memorial.model.UserVerifyModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiInterface {

    @POST("users")
    fun getUserVerify(
            @Field("phone") phone: String
    ): Call<UserVerifyModel>

}
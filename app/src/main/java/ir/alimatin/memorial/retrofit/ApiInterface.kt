package ir.alimatin.memorial.retrofit

import ir.alimatin.memorial.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("userVerifyCodes")
    fun sendMobile(
        @Field("phone") phone: String
    ): Call<UserVerifyModel>

    @GET("posts")
    fun posts(): Call<List<PostsModelItem>>

    @FormUrlEncoded
    @POST("posts")
    fun post(
        @Field("user") user: String,
        @Field("medias") medias: String,
        @Field("caption") caption: String,
        @Field("location") location: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
        @Field("comment") comment: Int,
        @Field("rank") rank: Int,
        @Field("ghost") ghost: Int,
        @Field("facebook") facebook: Int,
        @Field("twitter") twitter: Int,
        @Field("telegram") telegram: Int,
        @Field("privacy") privacy: Int,
        @Field("sendLater") sendLater: String
    ): Call<CreatePostsModel>

    @POST("uploadFiles/upload")
    fun uploadFile(
        @Body requestBody: RequestBody
    ): Call<UploadModel>

    @FormUrlEncoded
    @POST("users")
    fun getUserVerify(
        @Field("phone") phone: String
    ): Call<UserVerifyModel>

}
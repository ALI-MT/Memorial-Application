package ir.alimatin.memorial._net

import ir.alimatin.memorial._dataModel.DataModelUser
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class ApiService {

    val BASE_URL = "localhost:3000/"

    interface DataApi {
        @POST("users")
        fun getTimeNow(
            @Path("phone") phone: String
        ): Call<DataModelUser>

        @POST("user/search/{codeMelli}")
        fun sendCode(
                @Path("codeMelli") codeMelli: String,
                @Query("send_sms") sendSms: Boolean = true
        ): Call<DataModelUser>

        /*@POST("user/search/{codeMelli}")
        fun sendCode(
            @Path("codeMelli") codeMelli: String,
            @Query("send_sms") sendSms: Boolean = true
        ): Call<DataModelLogin>

        @POST("user/checkcode/{id}")
        fun checkCode(
            @Path("id") id: Int,
            @Query("code") code: String
        ): Call<DataModelVerifyCode>

        @POST("student/curriculum/{id}")
        fun getSchedule(
            @Path("id") id: Int
        ): Call<DataModelSchedule>

        @POST("user/getuser/{id}")
        fun getProfile(
            @Path("id") id: Int
        ): Call<DataModelProfile>

        @POST("student/myteachers/{id}")
        fun getTeachers(
            @Path("id") id: Int
        ): Call<List<DataModelTeacher>>*/
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(25, TimeUnit.SECONDS)
        .build()

    fun getApi(): DataApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build().create(DataApi::class.java)
    }
}
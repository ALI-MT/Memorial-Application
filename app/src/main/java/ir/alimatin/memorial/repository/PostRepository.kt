package ir.alimatin.memorial.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ir.alimatin.memorial.model.CreatePostsModel
import ir.alimatin.memorial.model.PostsModelItem
import ir.alimatin.memorial.model.UploadModel
import ir.alimatin.memorial.retrofit.RetrofitClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PostRepository {

    val setPost = MutableLiveData<CreatePostsModel>()
    val posts = MutableLiveData<List<PostsModelItem>>()
    val upload = MutableLiveData<UploadModel>()


    fun setPostApi(
        user: String,
        medias: String,
        caption: String,
        location: String,
        latitude: String,
        longitude: String,
        comment: Int,
        rank: Int,
        ghost: Int,
        facebook: Int,
        twitter: Int,
        telegram: Int,
        privacy: Int,
        sendLater: String
    ): MutableLiveData<CreatePostsModel> {
        val call = RetrofitClient.apiInterface.post(
            user,
            medias,
            caption,
            location,
            latitude,
            longitude,
            comment,
            rank,
            ghost,
            facebook,
            twitter,
            telegram,
            privacy,
            sendLater
        )

        call.enqueue(object : Callback<CreatePostsModel> {
            override fun onFailure(call: Call<CreatePostsModel>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<CreatePostsModel>,
                response: Response<CreatePostsModel>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                response.body()?.apply {
                    setPost.value = CreatePostsModel(
                        status = this.status,
                        _id = this._id,
                        __v = this.__v,
                        caption = this.caption,
                        comment = this.comment,
                        dateCreated = this.dateCreated,
                        facebook = this.facebook,
                        ghost = this.ghost,
                        latitude = this.latitude,
                        location = this.location,
                        longitude = this.longitude,
                        medias = this.medias,
                        privacy = this.privacy,
                        rank = this.rank,
                        sendLater = this.sendLater,
                        telegram = this.telegram,
                        twitter = this.twitter,
                        user = this.user
                    )
                }

            }
        })
        return setPost
    }

    fun getPostApi(): MutableLiveData<List<PostsModelItem>> {
        val call = RetrofitClient.apiInterface.posts()

        call.enqueue(object : Callback<List<PostsModelItem>> {
            override fun onFailure(call: Call<List<PostsModelItem>>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<PostsModelItem>>,
                response: Response<List<PostsModelItem>>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                response.body()?.apply {
                    posts.value = this
                }

            }
        })
        return posts
    }

    fun uploadImages(
        requestBody: RequestBody
    ): MutableLiveData<UploadModel> {

        val call = RetrofitClient.apiInterface.uploadFile(requestBody)

        call.enqueue(object : Callback<UploadModel> {
            override fun onFailure(call: Call<UploadModel>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<UploadModel>,
                response: Response<UploadModel>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                response.body()?.apply {
                    upload.value = UploadModel(
                        destination = destination,
                        size = size,
                        encoding = encoding,
                        mimetype = mimetype,
                        fieldname = filename,
                        originalname = originalname,
                        path = path,
                        filename = filename
                    )
                }

            }
        })
        return upload
    }

}
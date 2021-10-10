package ir.alimatin.memorial.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.alimatin.memorial.model.CreatePostsModel
import ir.alimatin.memorial.model.UploadModel
import ir.alimatin.memorial.model.UserVerifyModel
import ir.alimatin.memorial.repository.PostRepository
import ir.alimatin.memorial.utilities.AppUtilities
import ir.alimatin.memorial.utilities.ProgressRequestBody
import java.io.File

class PostViewModel : ViewModel() {

    var setPostLiveData: MutableLiveData<CreatePostsModel>? = null
    var uploadLiveData: MutableLiveData<UploadModel>? = null


    fun setPost(
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
    ): LiveData<CreatePostsModel> {
        setPostLiveData = PostRepository.setPostApi(
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
        return setPostLiveData as MutableLiveData<CreatePostsModel>
    }

    fun uploadImage(
        image: File,
        uploadCallbacksListener: ProgressRequestBody.UploadCallbacks? = null
    ): LiveData<UploadModel> {

        val imageMap: HashMap<String, File?> = HashMap()
        imageMap["sampleFile"] = image

        uploadLiveData = PostRepository.uploadImages(
            AppUtilities.requestBodyBuilder(
                imageMap,
                uploadCallbacksListener
            )
        )
        return uploadLiveData as MutableLiveData<UploadModel>
    }

}
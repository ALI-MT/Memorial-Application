package ir.alimatin.memorial.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.alimatin.memorial.model.UserVerifyModel
import ir.alimatin.memorial.repository.UserVerifyActivityRepository

class UserVerifyActivityViewModel : ViewModel() {

    var sendMobileLiveData: MutableLiveData<UserVerifyModel>? = null

    fun sendMobile(mobile: String): LiveData<UserVerifyModel> {
        sendMobileLiveData = UserVerifyActivityRepository.sendMobileApi(mobile)
        return sendMobileLiveData as MutableLiveData<UserVerifyModel>
    }

}
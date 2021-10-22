package ir.alimatin.memorial.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.alimatin.memorial.model.UserVerifyModel
import ir.alimatin.memorial.repository.UserVerifyActivityRepository

class UserVerifyActivityViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<UserVerifyModel>? = null

    fun getUserVerify() : LiveData<UserVerifyModel>? {
        servicesLiveData = UserVerifyActivityRepository.getUserVerifyApiCall()
        return servicesLiveData
    }

}
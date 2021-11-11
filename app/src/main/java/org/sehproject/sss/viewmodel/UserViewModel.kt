package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.utils.SingleLiveEvent

class UserViewModel : ViewModel() {
    val userLogic = UserLogic(this)

    val userId = MutableLiveData("")
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")
    val nickName = MutableLiveData("")
    val loginEvent = SingleLiveEvent<Any>()
    val registerEvent = SingleLiveEvent<Any>()
}
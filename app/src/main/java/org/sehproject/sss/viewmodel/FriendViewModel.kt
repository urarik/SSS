package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.datatype.User
import org.sehproject.sss.utils.SingleLiveEvent

class FriendViewModel : ViewModel() {
    val friendLogic = FriendLogic(this)

    val userIdOrNickName = MutableLiveData<String>()
    val userList = MutableLiveData<ArrayList<User>>()
    val friendList = MutableLiveData<ArrayList<User>>()
    val searchUserEvent = SingleLiveEvent<Any>()
    val addFriendEvent = SingleLiveEvent<Any>()
    val listFriendEvent = SingleLiveEvent<Any>()
    val deleteFriendEvent = SingleLiveEvent<Any>()
    val blockFriendEvent = SingleLiveEvent<Any>()
}
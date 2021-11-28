package org.sehproject.sss.viewmodel

import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.datatype.User
import org.sehproject.sss.logic.FriendLogic
import org.sehproject.sss.repository.FriendRepository
import org.sehproject.sss.utils.SingleLiveEvent

class FriendViewModel : ViewModel() {
    val friendLogic = FriendLogic(this)
    val friendRepository = FriendRepository()

    val userList = MutableLiveData<List<User>>()
    val friendList = MutableLiveData<List<User>>()
    val searchUserEvent = SingleLiveEvent<Any>()
    val addFriendEvent = SingleLiveEvent<AppCompatButton>()
    val deleteFriendEvent = SingleLiveEvent<Any>()
    val blockFriendEvent = SingleLiveEvent<Any>()
    val listFriendEvent = SingleLiveEvent<String>()
    val addFriendFailEvent = SingleLiveEvent<Any>()

    fun setFriendList() {
        friendRepository.getFriendList() {code, list ->
            if(code == 0) {
                friendList.value = list
            }
        }
    }
}
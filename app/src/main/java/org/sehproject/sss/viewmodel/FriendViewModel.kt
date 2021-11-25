package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.datatype.User
import org.sehproject.sss.logic.FriendLogic
import org.sehproject.sss.repository.FriendRepository
import org.sehproject.sss.utils.SingleLiveEvent

class FriendViewModel : ViewModel() {
    val friendLogic = FriendLogic(this)
    val friendRepository = FriendRepository()

    val userIdOrNickName = MutableLiveData<String>()
    // ArrayList -> List로 변경
    val userList = MutableLiveData<List<User>>()
    val friendList = MutableLiveData<List<User>>()
    val searchUserEvent = SingleLiveEvent<Any>()
    val addFriendEvent = SingleLiveEvent<Any>()
    val deleteFriendEvent = SingleLiveEvent<Any>()
    val blockFriendEvent = SingleLiveEvent<Any>()
    val listFriendEvent = SingleLiveEvent<Any>()

    // Logic에서 ViewModel로 변경, ArrayList -> List로 변경, Return 삭제
    fun getFriendList() {
//        val temp = mutableListOf<User>()
//        temp.add(User(userId = "1234", nickName = "김아무개", isOnline = true, isAttend = true))
//        temp.add(User(userId = "234543534", nickName = "금정욱", isOnline = false, isAttend = false))
//        temp.add(User(userId = "3456", nickName = "정수용", isOnline = true, isAttend = false))
//        temp.add(User(userId = "4", nickName = "최문빈", isOnline = false, isAttend = true))
//        temp.add(User(userId = "7890", nickName = "이소연", isOnline = true, isAttend = true))
//        friendList.value = temp

        friendRepository.getFriendList() {code, list ->
            if(code == 0)
                friendList.value = list
        }
    }
}
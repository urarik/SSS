package org.sehproject.sss.logic

import android.util.Log
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.FriendViewModel

class FriendLogic(val friendViewModel: FriendViewModel) {
    val friendRepository = friendViewModel.friendRepository

    fun onSearchUserClick() {
        friendViewModel.searchUserEvent.call()
    }

    // 함수 추가
    fun onSearchUserCompleteClick(name: String) {
        val temp = mutableListOf<User>()
        temp.add(User(userId = "1234", nickName = "김아무개", isOnline = true, isAttend = true))
        temp.add(User(userId = "234543534", nickName = "금정욱", isOnline = false, isAttend = false))
        temp.add(User(userId = "3456", nickName = "정수용", isOnline = true, isAttend = true))
        temp.add(User(userId = "4", nickName = "최문빈", isOnline = true, isAttend = true))
        temp.add(User(userId = "7890", nickName = "이소연", isOnline = true, isAttend = true))
        friendViewModel.userList.value = temp

//        friendRepository.searchUser(name) {code, list ->
//            if(code == 0)
//                friendViewModel.userList.value = list
//        }
    }

    fun onAddFriendClick(friendId: String) {
        friendRepository.addFriend(friendId) {code ->
            if(code == 0)
                Log.d("TAG", "test")
        }
    }

    fun onDeleteFriendClick(friendId: String) {
        friendRepository.deleteFriend(friendId) {code ->
            if(code == 0)
                Log.d("TAG", "test")
        }
    }

    fun onBlockFriendClick(friendId: String) {
        friendRepository.blockFriend(friendId) {code ->
            if(code == 0)
                Log.d("TAG", "test")
        }
    }

    fun onViewFriendPageClick(friendId: String) {

    }
}
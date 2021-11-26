package org.sehproject.sss.logic

import android.util.Log
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.FriendViewModel

class FriendLogic(val friendViewModel: FriendViewModel) {

    fun onSearchUserClick() {
        friendViewModel.searchUserEvent.call()
    }

    // 함수 추가
    fun onSearchUserCompleteClick(name: String) {
        friendViewModel.friendRepository.searchUser(name) {code, list ->
            if(code == 0)
                friendViewModel.userList.value = list
        }
    }

    fun onAddFriendClick(friendId: String) {
        friendViewModel.friendRepository.addFriend(friendId) {code ->
            if(code == 0)
                Log.d("TAG", "test")
        }
    }

    fun onDeleteFriendClick(friendId: String) {
        friendViewModel.friendRepository.deleteFriend(friendId) {code ->
            if(code == 0)
                Log.d("TAG", "test")
        }
    }

    fun onBlockFriendClick(friendId: String) {
        friendViewModel.friendRepository.blockFriend(friendId) {code ->
            if(code == 0)
                Log.d("TAG", "test")
        }
    }

    fun onViewFriendPageClick(friendId: String) {
        friendViewModel.listFriendEvent.value = friendId
    }
}
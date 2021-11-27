package org.sehproject.sss.logic

import android.util.Log
import androidx.appcompat.widget.AppCompatButton
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

    fun onAddFriendClick(friendId: String, button: AppCompatButton) {
        //addFriendEvent is called when a user adds the user for hiding adding button.
        //It have to be improved by putting "friend" attribute in User data class
        //in order to hide adding button latter.
        friendViewModel.friendRepository.addFriend(friendId) {code ->
            if(code == 0)
                friendViewModel.addFriendEvent.value = button
        }
    }

    fun onDeleteFriendClick(friendId: String) {
        friendViewModel.friendRepository.deleteFriend(friendId) {code ->
            if(code == 0)
                friendViewModel.deleteFriendEvent.call()
        }
    }

    fun onBlockFriendClick(friendId: String) {
        friendViewModel.friendRepository.blockFriend(friendId) {code ->
            if(code == 0)
                friendViewModel.blockFriendEvent.call()
        }
    }

    fun onViewFriendPageClick(friendId: String) {
        friendViewModel.listFriendEvent.value = friendId
    }
}
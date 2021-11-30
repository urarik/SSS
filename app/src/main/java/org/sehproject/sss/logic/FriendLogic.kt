package org.sehproject.sss.logic

import androidx.appcompat.widget.AppCompatButton
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.FriendViewModel

class FriendLogic(val friendViewModel: FriendViewModel) {

    fun onSearchUserClick() {
        friendViewModel.searchUserEvent.call()
    }

    fun onSearchUserCompleteClick(name: String) {
        friendViewModel.friendRepository.searchUser(name) {code, list ->
            var cnt = 0
            var mlist: MutableList<User>? = null
            if (list != null)
                mlist = list as MutableList<User>
            else
                mlist = mutableListOf()
            if(code == 0) {
                for (i in mlist) {
                    if (i.userId == UserInfo.userId) {
                        mlist.removeAt(cnt)
                        break
                    }
                    cnt++
                }
            }
            friendViewModel.userList.value = mlist
        }
    }

    fun onAddFriendClick(friendId: String, button: AppCompatButton) {
        var mlist: MutableList<User>? = null
        var isFriend = false
        friendViewModel.friendRepository.getFriendList() {code, list ->
            if (code == 0) {
                mlist = list as MutableList<User>
                if (mlist != null)
                    for (i in mlist!!) {
                        if (i.userId == friendId) {
                            isFriend = true
                            friendViewModel.addFriendFailEvent.call()
                        }
                    }
                //It have to be improved by putting "friend" attribute in User data class
                //in order to hide add button latter
                if (!isFriend) {
                    friendViewModel.friendRepository.addFriend(friendId) {code2 ->
                        if(code2 == 0)
                            friendViewModel.addFriendEvent.value = button
                    }
                }
            }
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
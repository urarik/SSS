package org.sehproject.sss.logic

import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import org.sehproject.sss.UserInfo
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
            var cnt = 0
            val mlist: MutableList<User>? = list as MutableList<User>
            if(code == 0)
                if (mlist != null)
                    for (i in mlist) {
                        if (i.userId == UserInfo.userId) {
                            mlist.removeAt(cnt)
                            break
                        }
                        cnt++
                    }
            Log.d("TAG", cnt.toString() + mlist.toString())
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

                //addFriendEvent is called when a user adds the user for hiding adding button.
                //It have to be improved by putting "friend" attribute in User data class
                //in order to hide adding button latter
                if (!isFriend) {
                    friendViewModel.friendRepository.addFriend(friendId) {code ->
                        if(code == 0)
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
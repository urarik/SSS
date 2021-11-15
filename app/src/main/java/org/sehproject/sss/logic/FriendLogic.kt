package org.sehproject.sss.logic

import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.FriendViewModel

class FriendLogic(val friendViewModel: FriendViewModel) {
    fun onSearchUserClick()
    {
        friendViewModel.searchUserEvent.call()
    }

    fun onAddFriendClick(friendId: String)
    {

    }

    fun getFriendList(): ArrayList<User>
    {
        var list = ArrayList<User>()
        return list
    }

    fun onDeleteFriendClick(friendId: String)
    {

    }

    fun onBlockFriendClick(friendId: String)
    {

    }

    fun onViewFriendPageClick(friendId: String)
    {

    }
}
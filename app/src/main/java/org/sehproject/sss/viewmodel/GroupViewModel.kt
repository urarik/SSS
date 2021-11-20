package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.datatype.Group
import org.sehproject.sss.datatype.User
import org.sehproject.sss.logic.GroupLogic
import org.sehproject.sss.repository.FriendRepository
import org.sehproject.sss.repository.GroupRepository
import org.sehproject.sss.utils.SingleLiveEvent

class GroupViewModel : ViewModel() {
    val groupRepository = GroupRepository()
    val friendRepository = FriendRepository()

    fun setFriendList() {
        friendRepository.getFriendList { _, list ->
            friendListLiveData.value = list
        }
    }

    val groupLogic = GroupLogic(this)

    val createGroupEvent = SingleLiveEvent<Int>()
    val createGroupCompleteEvent = SingleLiveEvent<Any>()
    val deleteGroupCompleteEvent = SingleLiveEvent<Any>()
    val editGroupEvent = SingleLiveEvent<Group>()
    val editGroupCompleteEvent = SingleLiveEvent<Any>()
    val inviteGroupEvent = SingleLiveEvent<Int>()
    val inviteGroupCompleteEvent = SingleLiveEvent<Any>()
    val kickOutGroupEvent = SingleLiveEvent<Int>()
    val kickOutGroupCompleteEvent = SingleLiveEvent<Any>()
    val viewGroupDetailsEvent = SingleLiveEvent<Int>()
    val viewGroupDetailsCompleteEvent = SingleLiveEvent<Any>()
    val viewGroupEvent = SingleLiveEvent<Any>()
    val viewGroupCompleteEvent = SingleLiveEvent<Any>()
    val inputGroupInfoProcessEvent = SingleLiveEvent<Any>()
    val friendListLiveData = MutableLiveData<List<User>>()
}
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
    val groupLogic = GroupLogic(this)
    val groupRepository = GroupRepository()
    val friendRepository = FriendRepository()


    fun setFriendList() {
        friendRepository.getFriendList { _, list ->
            friendListLiveData.value = list
        }
    }
    fun getGroupList()
    {
        val tests = mutableListOf<Group>()
        tests.add(Group(gid=0, color=0xebe834, name="test1"))
        tests.add(Group(gid=1, color=0xc436c7, name="test2"))
        tests.add(Group(gid=2, color=0x3073c9, name="test3"))
        tests.add(Group(gid=3, color=0x36d9c8, name="test4"))
        groupListLiveData.value = tests
/*        groupRepository.getGroupList { i, list ->
        if(i == 0)
            groupListLiveData.value = list
        }*/
    }
    fun setGroup(gid: Int?) {
        groupLiveData.value = Group(0,
        "SE Team",
        "Software engineering team for a project.",
        0xFFA103,
        participants = mutableListOf(User("test1", "test1"),
            User("test1", "test2"),
            User("test1", "test3"),
            User("test1", "test4"),))

//        if(gid == null) groupLiveData.value = Group()
//        else
//            groupRepository.getGroup(gid) {code, group ->
//                if(code == 0)
//                    groupLiveData.value = group
//            }
    }


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
    val groupLiveData = MutableLiveData<Group>()
    val groupListLiveData = MutableLiveData<List<Group>>()
}
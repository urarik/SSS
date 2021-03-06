package org.sehproject.sss.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.UserInfo
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

    val createGroupEvent = SingleLiveEvent<Int>()
    val setColorEvent = SingleLiveEvent<Group>()
    val createGroupCompleteEvent = SingleLiveEvent<Group>()
    val deleteGroupEvent = SingleLiveEvent<Int>()
    val deleteGroupCompleteEvent = SingleLiveEvent<Any>()
    val editGroupEvent = SingleLiveEvent<Group>()
    val editGroupCompleteEvent = SingleLiveEvent<Group>()
    val inviteGroupEvent = SingleLiveEvent<Int>()
    val inviteGroupCompleteEvent = SingleLiveEvent<Any>()
    val kickOutGroupEvent = SingleLiveEvent<Int>()
    val kickOutGroupCompleteEvent = SingleLiveEvent<Any>()
    val viewGroupDetailsEvent = SingleLiveEvent<Int>()
    val exitGroupCompleteEvent = SingleLiveEvent<Any>()
    val sortEvent = SingleLiveEvent<Any>()
    val acceptGroupInviteEvent = SingleLiveEvent<Any>()
    val refuseGroupInviteEvent = SingleLiveEvent<Any>()
    val friendListLiveData = MutableLiveData<List<User>>()
    val groupLiveData = MutableLiveData<Group>()
    val groupListLiveData = MutableLiveData<List<Group>>()
    val selectedGroupUserList = mutableListOf<String>()
    val cancelInviteGroupEvent = SingleLiveEvent<Any>()
    val editGroupFailEvent = SingleLiveEvent<String>()

    fun setFriendList(gid: Int) {
        if(gid == -1)
            friendRepository.getFriendList { _, list ->
                friendListLiveData.value = list
            }
        else
            groupRepository.getParticipantList(gid) {code, list ->
                var cnt = 0
                var mlist: MutableList<User>? = null
                mlist = if (list != null)
                    list as MutableList<User>
                else
                    mutableListOf()

                if(code == 0) {
                    for (i in mlist) {
                        if (i.userId == UserInfo.userId) {
                            mlist.removeAt(cnt)
                            break
                        }
                        cnt++
                    }
                    friendListLiveData.value = mlist
                }
            }
    }
    fun setGroupList()
    {
       groupRepository.getGroupList { i, list ->
           var mlist: MutableList<Group>? = null
           if(i == 0) {
                mlist = list as MutableList<Group>
                groupListLiveData.value = groupLogic.sortGroupByName(mlist)
           }
        }
    }
    fun setGroup(gid: Int?) {
        if(gid == null) groupLiveData.value = Group()
        else {
            groupRepository.getGroup(gid) { code, group ->
                Log.d("TAG", group.toString())
                if (code == 0) {
                    groupLiveData.value = group
                }
            }
            groupRepository.getParticipantList(gid) { code, participants ->
                if(code ==0)
                    friendListLiveData.value = participants
                else if (code == 1)
                    friendListLiveData.value = listOf()
            }
        }

    }
}
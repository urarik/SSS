package org.sehproject.sss.logic

import org.sehproject.sss.datatype.Group
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.GroupViewModel

class GroupLogic(private val groupViewModel: GroupViewModel) {
    fun onCreateGroupClick()
    {
        groupViewModel.createGroupEvent.call()
    }

    fun onCreateGroupCompleteClick(group: Group)
    {
        groupViewModel.groupRepository.createGroup(group) {code ->
            if(code == 0) {
                groupViewModel.createGroupCompleteEvent.value = group
            }
        }
    }
    fun onColorChooseClick(group: Group) {
        groupViewModel.setColorEvent.value = group
    }

    fun onDeleteGroupClick(gid: Int)
    {
        groupViewModel.deleteGroupEvent.value = gid
    }
    fun onDeleteGroupConfirmClick(gid: Int) {
        groupViewModel.groupRepository.deleteGroup(gid) { code ->
            if (code == 0)
                groupViewModel.deleteGroupCompleteEvent.call()
        }
    }

    fun onEditGroupClick(group: Group)
    {
        groupViewModel.editGroupEvent.value = group
    }

    fun onEditGroupCompleteClick(group: Group)
    {
        if(group.gid == null) onCreateGroupCompleteClick(group)
        else {
            groupViewModel.groupRepository.editGroup(group) { code ->
                if(code == 0)
                    groupViewModel.editGroupCompleteEvent.call()
            }
        }
    }

    fun onAcceptGroupClick(gid: Int)
    {
        groupViewModel.groupRepository.acceptGroup(gid, true) {code ->
            if(code == 0)
                groupViewModel.acceptGroupInviteEvent.call()
        }
    }

    fun onRefuseGroupClick(gid: Int)
    {
        groupViewModel.groupRepository.acceptGroup(gid, false) {code ->
            if(code == 0)
                groupViewModel.refuseGroupInviteEvent.call()
        }
    }

    fun onKickOutGroupClick(gid: Int) {
        groupViewModel.kickOutGroupEvent.value = gid
    }

    fun onKickOutGroupCompleteClick(gid: Int) {
        groupViewModel.groupRepository.kickOutGroup(gid, groupViewModel.selectedGroupUserList) { code: Int ->
            if(code == 0) {
                groupViewModel.kickOutGroupCompleteEvent.call()
            }
        }
    }

    fun onExitGroupClick(gid: Int) {
        groupViewModel.groupRepository.exitGroup(gid) { code ->
            if (code == 0) {
                groupViewModel.exitGroupCompleteEvent.call()
            }
        }
    }

    fun onInviteGroupClick(gid: Int)
    {
        groupViewModel.inviteGroupEvent.value = gid
    }

    fun onInviteGroupDoneClick(gid: Int)
    {
        groupViewModel.groupRepository.inviteGroup(gid, groupViewModel.selectedGroupUserList) { code: Int ->
            if(code == 0) {
                groupViewModel.inviteGroupCompleteEvent.call()
            }
        }
    }

    fun onViewGroupDetailClick(gid: Int)
    {
        groupViewModel.viewGroupDetailsEvent.value = gid
    }


    fun onSortingClick(pos: Int) {
        if (pos == 0 && groupViewModel.groupListLiveData.value != null) {
            groupViewModel.groupListLiveData.value =
                sortGroupByName(groupViewModel.groupListLiveData.value!!)
            groupViewModel.sortEvent.call()
        } else if (pos == 1 && groupViewModel.groupListLiveData.value != null) {
            groupViewModel.groupListLiveData.value =
                sortGroupByMembers(groupViewModel.groupListLiveData.value!!)
            groupViewModel.sortEvent.call()
        }
    }

    fun onItemClick(user: User) {
        groupViewModel.selectedGroupUserList.add(user.userId)
    }

    fun sortGroupByName(list: List<Group>): List<Group> {
        return list.sortedBy { it.name }
    }

    fun sortGroupByMembers(list: List<Group>): List<Group> {
        return list.sortedWith(compareBy({ it.participants?.size }, { it.name }))
    }
}
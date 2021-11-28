package org.sehproject.sss.logic

import android.util.Log
import org.sehproject.sss.datatype.Group
import org.sehproject.sss.datatype.Plan
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
        Log.d("TAG", "edit")
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

    fun onAcceptGroupClick()
    {
        //groupViewModel.groupRepository.acceptGroup()
        groupViewModel.acceptGroupInviteEvent.call()
    }

    fun onRefuseGroupClick()
    {
        //groupViewModel.groupRepository.acceptGroup()
        groupViewModel.refuseGroupInviteEvent.call()
    }

    fun onKickOutGroupClick(gid: Int) {
        groupViewModel.kickOutGroupEvent.value = gid
    }

    fun onKickOutGroupCompleteClick(gid: Int) {
        groupViewModel.groupRepository.kickOutGroup(gid, groupViewModel.selectedGroupUserList) { code: Int ->
            Log.d("TAG", code.toString())
            if(code == 0) {
                groupViewModel.kickOutGroupCompleteEvent.call()
            }
        }
    }

    fun onExitGroupClick(gid: Int) {
        groupViewModel.groupRepository.exitGroup(gid) { code ->

        }
    }

    fun onInviteGroupClick(gid: Int)
    {
        groupViewModel.inviteGroupEvent.value = gid
    }

    fun onInviteGroupDoneClick(gid: Int)
    {
        groupViewModel.groupRepository.inviteGroup(gid, groupViewModel.selectedGroupUserList) { code: Int ->
            Log.d("TAG", code.toString())
            if(code == 0) {
                groupViewModel.inviteGroupCompleteEvent.call()
            }
        }
    }

    fun onViewGroupDetailClick(gid: Int)
    {
        groupViewModel.viewGroupDetailsEvent.value = gid
    }


    fun getGroup(groupid: Int)
    {

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
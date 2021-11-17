package org.sehproject.sss.logic

import android.util.Log
import org.sehproject.sss.datatype.Group
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.GroupViewModel

class GroupLogic(private val groupViewModel: GroupViewModel) {
    fun onCreateGroupClick()
    {
        groupViewModel.createGroupEvent.call()
    }

    fun onCreateGroupCompleteClick()
    {

    }

    fun onDeleteGroupClick(groupId: Int)
    {
        groupViewModel.groupRepository.deleteGroup(groupId) {

        }
    }

    fun onDeleteGroupCompleteClick()
    {

    }

    fun onEditGroupClick(group: Group)
    {
        Log.d("TAG", "edit")
        groupViewModel.editGroupEvent.value = group
    }

    fun onEditGroupCompleteClick(gid: Int)
    {

    }

    fun onAcceptGroupClick(groupid: Int)
    {

    }

    fun onRefuseGroupClick(groupId: Int)
    {

    }

    fun onKickOutGroupClick(gid: Int) {
        groupViewModel.kickOutGroupEvent.value = gid
    }

    fun onExitGroupClick(gid: Int) {
    //    groupViewModel.groupRepository.exitGroup(gid) { code ->
    //
    //    }
    }

    fun onInviteGroupClick(gid: Int)
    {
        groupViewModel.inviteGroupEvent.value = gid
    }

    fun onInviteGroupDoneClick()
    {

    }

    fun onViewGroupDetailClick()
    {

    }

    fun getGroupList()
    {

    }

    fun getGroup(groupid: Int)
    {

    }

    fun onItemClick(user: User) {

    }
}
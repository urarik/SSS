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

    fun onDeleteGroupCompleteClick()
    {

    }

    fun onEditGroupClick(group: Group)
    {
        Log.d("TAG", "edit")
        groupViewModel.editGroupEvent.value = group
    }

    fun onEditGroupCompleteClick(group: Group)
    {
        groupViewModel.editGroupCompleteEvent.value = group
//        if(group.gid == null) onCreateGroupCompleteClick(group)
//        else {
//            groupViewModel.groupRepository.editGroup(group) { code ->
//                if(code == 0)
//                    groupViewModel.editGroupCompleteEvent.call()
//            }
//        }
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

    fun onViewGroupDetailClick(gid: Int)
    {
        groupViewModel.viewGroupDetailsEvent.value = gid
    }


    fun getGroup(groupid: Int)
    {

    }

    fun onItemClick(user: User) {

    }
}
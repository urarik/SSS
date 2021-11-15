package org.sehproject.sss.logic

import android.util.Log
import org.sehproject.sss.viewmodel.GroupViewModel

class GroupLogic(val groupViewModel: GroupViewModel) {
    fun onCreateGroupClick()
    {

    }

    fun onCreateGroupCompleteClick()
    {

    }

    fun onDeleteGroupClick()
    {

    }

    fun onDeleteGroupCompleteClick()
    {

    }

    fun onEditGroupClick()
    {

    }

    fun onEditGroupCompleteClick()
    {

    }

    fun onAcceptGroupClick(groupid: Int)
    {

    }

    fun onRefuseGroupClick(groupid: Int)
    {

    }

    fun onInviteGroupClick()
    {
        Log.d("TAG", "!@#")
        groupViewModel.inviteGroupEvent.call()
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

    fun onItemClick(user: Friend) {

    }
}
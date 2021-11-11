package org.sehproject.sss.viewmodel

import androidx.lifecycle.ViewModel
import org.sehproject.sss.logic.GroupLogic
import org.sehproject.sss.utils.SingleLiveEvent

class GroupViewModel : ViewModel() {
    val groupLogic = GroupLogic(this)

    val createGroupEvent = SingleLiveEvent<Any>()
    val createGroupCompleteEvent = SingleLiveEvent<Any>()
    val deleteGroupEvent = SingleLiveEvent<Any>()
    val deleteGroupCompleteEvent = SingleLiveEvent<Any>()
    val editGroupEvent = SingleLiveEvent<Any>()
    val editGroupCompleteEvent = SingleLiveEvent<Any>()
    val inviteGroupEvent = SingleLiveEvent<Any>()
    val inviteGroupCompleteEvent = SingleLiveEvent<Any>()
    val kickOutGroupEvent = SingleLiveEvent<Any>()
    val kickOutGroupCompleteEvent = SingleLiveEvent<Any>()
    val viewGroupDetailsEvent = SingleLiveEvent<Any>()
    val viewGroupDetailsCompleteEvent = SingleLiveEvent<Any>()
    val viewGroupEvent = SingleLiveEvent<Any>()
    val viewGroupCompleteEvent = SingleLiveEvent<Any>()
    val inputGroupInfoProcessEvent = SingleLiveEvent<Any>()
}
package org.sehproject.sss.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.api.services.calendar.model.Event
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.*
import org.sehproject.sss.logic.PlanLogic
import org.sehproject.sss.repository.FriendRepository
import org.sehproject.sss.repository.PlanRepository
import org.sehproject.sss.utils.SingleLiveEvent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PlanViewModel : ViewModel() {
    val planLogic = PlanLogic(this)
    val planRepository = PlanRepository()
    val friendRepository = FriendRepository()

    fun setFriendList() {
        friendRepository.getFriendList { _, list ->
            friendListLiveData.value = list
        }
    }

    fun getPlanList(isCurrent: Boolean, userId: String = UserInfo.userId) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm")
        val formatted = current.format(formatter)

        Log.d("TAG", formatted)

        planRepository.getPlanList(userId, isCurrent) {code, list ->
            if(code == 0) {
                if (isLastPlan.value == true) {
                    planListLiveData.value = list?.filter {
                        it.endTime < formatted
                    }
                } else {
                    planListLiveData.value = list?.filter {
                        it.endTime >= formatted
                    }
                }

            }
            else if(code == 1) {
                planListLiveData.value = listOf()
            }
        }
    }

    fun setPlan(pid: Int) {
        planRepository.getPlan(pid) {code, plan ->
            if(code == 0)
                planLiveData.value = plan
       }
        planRepository.getMemoList(pid) {code, memos ->
            if(code ==0)
                memoListLiveData.value = memos
            else if(code == 1)
                memoListLiveData.value = listOf()
        }
        planRepository.getParticipantList(pid) {code, participants ->
            if(code == 0)
                userListLiveData.value = participants
            else if (code == 1)
                userListLiveData.value = listOf()
        }
    }

    val editPlanEvent = SingleLiveEvent<Plan>()
    val editCompletePlanEvent = SingleLiveEvent<Int>()
    val startDatePickEvent = SingleLiveEvent<Plan>()
    val endDatePickEvent = SingleLiveEvent<Plan>()
    val deletePlanEvent = SingleLiveEvent<Int>()
    val deletePlanCompleteEvent = SingleLiveEvent<Int>()
    val completePlanCompleteEvent = SingleLiveEvent<Int>()
    val kickOutPlanEvent = SingleLiveEvent<Int>()
    val kickOutPlanCompleteEvent = SingleLiveEvent<Int>()
    val cancelPlanEvent = SingleLiveEvent<Any>()
    val cancelPlanCompleteEvent = SingleLiveEvent<Int>()
    val createMemoEvent = SingleLiveEvent<Any>()
    val createMemoCompleteEvent = SingleLiveEvent<Int>()
    val deleteMemoCompleteEvent = SingleLiveEvent<Any>()
    val createPlanEvent = SingleLiveEvent<Any>()
    val createPlanOcrEvent = SingleLiveEvent<Any>()
    val createPlanOcrDoneEvent = SingleLiveEvent<Plan>()
    val uploadImgEvent = SingleLiveEvent<Any>()
    val createPlanTypeEvent = SingleLiveEvent<Any>()
    val createPlanCompleteEvent = SingleLiveEvent<Int>()
    val viewPlanDetailsEvent = SingleLiveEvent<Int>()
    val makePlanPublicCompleteEvent = SingleLiveEvent<Int>()
    val invitePlanEvent = SingleLiveEvent<Int>()
    val invitePlanCompleteEvent = SingleLiveEvent<Int>()
    val acceptPlanInviteEvent = SingleLiveEvent<Any>()
    val refusePlanInviteEvent = SingleLiveEvent<Any>()
    val planListLiveData = MutableLiveData<List<Plan>>()
    val friendListLiveData = MutableLiveData<List<User>>()
    val planLiveData = MutableLiveData<Plan>()
    val memoListLiveData = MutableLiveData<List<Memo>>()
    val userListLiveData = MutableLiveData<List<User>>()
    val concatAdapterLiveData = MutableLiveData<Int>(0)
    val syncCalendarEvent = SingleLiveEvent<Event>()
    val isLastPlan = SingleLiveEvent<Boolean>()
    val selectedPlanUserList = mutableListOf<String>()
    var is_invite: Boolean = true
}
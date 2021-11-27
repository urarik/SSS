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

    fun getPlanList() {
        val temp = mutableListOf<Plan>()
        temp.add(Plan(pid = 0, name = "test", startTime = "2021-11-29 18:30", endTime = "2021-11-29 22:00",
            location = "영남대학교", isPublic = false, category = "MySchool"))
        temp.add(Plan(pid = 0, name = "test", startTime = "2020-11-25 13:30", endTime = "2020-11-25 18:00",
            location = "영남대학교", isPublic = true, category = "Wow"))
        temp.add(Plan(pid = 0, name = "test", startTime = "2021-11-29 13:30", endTime = "2021-11-29 14:00",
            location = "영남대학교", isPublic = false, category = "Hello world"))
        temp.add(Plan(pid = 0, name = "test", startTime = "2021-11-27 13:30", endTime = "2021-11-27 18:00",
            location = "영남대학교", isPublic = true, category = "MySchool"))

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm")
        val formatted = current.format(formatter)

        Log.d("TAG", formatted)

        if (isLastPlan.value!!) {
            planListLiveData.value = temp.filter {
                it.endTime < formatted
            }
        } else {
            planListLiveData.value = temp.filter {
                it.endTime >= formatted
            }
        }

//        planRepository.getPlanList(true) {code, list ->
//            if(code == 0) {
//                planListLiveData.value = temp.filter {
//                    it.endTime >= "2021-11-26 13:30"
//                }
//                lastPlanListLiveData.value = temp.filter {
//                    it.endTime < "2021-11-26 13:30"
//                }
//
//            }
//            else if(code == 1) {
//                planListLiveData.value = listOf()
//                lastPlanListLiveData.value = listOf()
//            }
//        }
    }

    fun setPlan(pid: Int) {

        planRepository.getPlan(pid) {code, plan ->
            if(code == 0)
                planLiveData.value = plan
       }
        planRepository.getMemoList(pid) {code, memos ->
            if(code ==0)
                memoListLiveData.value = memos
        }
        planRepository.getParticipantList(pid) {code, participants ->
            if(code == 0)
                userListLiveData.value = participants
        }
    }

    val editPlanEvent = SingleLiveEvent<Plan>()
    val editCompletePlanEvent = SingleLiveEvent<Int>()
    val startDatePickEvent = SingleLiveEvent<Plan>()
    val endDatePickEvent = SingleLiveEvent<Plan>()
    val deletePlanEvent = SingleLiveEvent<Int>()
    val deletePlanCompleteEvent = SingleLiveEvent<Int>()
    val completePlanCompleteEvent = SingleLiveEvent<Int>()
    val kickOutPlanEvent = SingleLiveEvent<Any>()
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
    val invitePlanEvent = SingleLiveEvent<Any>()
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
    val isLastPlan = SingleLiveEvent<Boolean>(false)

}
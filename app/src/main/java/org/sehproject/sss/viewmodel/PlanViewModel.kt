package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.api.services.calendar.model.Event
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.*
import org.sehproject.sss.logic.PlanLogic
import org.sehproject.sss.repository.FriendRepository
import org.sehproject.sss.repository.PlanRepository
import org.sehproject.sss.utils.SingleLiveEvent

class PlanViewModel: ViewModel() {
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
        temp.add(Plan(pid = 0, name="Meeting", startTime = "2021-10-12 12:33:50", endTime = "2022-12-01 11:20:32"))
        temp.add(Plan(pid = 1, name="Homework", startTime = "2021-10-12 12:33:50", endTime = "2022-12-01 11:20:32"))
        temp.add(Plan(pid = 2, name="School", startTime = "2021-10-12 12:33:50", endTime = "2022-12-01 11:20:32"))
        temp.add(Plan(pid = 3, name="Market", startTime = "2021-10-12 12:33:50", endTime = "2022-12-01 11:20:32"))
        temp.add(Plan(pid = 4, name="Meal", startTime = "2021-10-12 12:33:50", endTime = "2022-12-01 11:20:32"))
        planListLiveData.value = temp

//        planRepository.getPlanList(true) {code, list ->
//            if(code == 0)
//                planListLiveData.value = list
//        }
    }
    fun setPlan(pid: Int) {
        planLiveData.value = Plan(10,
            "Meeting",
            "2012-10-32 09:12:32",
            "2012-10-32 09:12:32",
        "YU",
        "school",
        UserInfo.userId,
            group=Group(0, "test3")
        )
        val temp = mutableListOf<Memo>()
        temp.add(Memo("Hello", 10, User(nickName = "Saehoon")))
        temp.add(Memo("Hi", 10, User(nickName = "Water")))
        memoListLiveData.value = temp
        val temp2 = mutableListOf<User>()
        temp2.add(User(nickName = "ABC", isOnline = false))
        temp2.add(User(nickName = "DEF", isOnline = true))
        temp2.add(User(nickName = "GHI", isOnline = false))
        temp2.add(User(nickName = "JKL", isOnline = true))
        userListLiveData.value = temp2

//        planRepository.getPlan(pid) {code, plan ->
//            if(code == 0)
//                planLiveData.value = plan
//        }
//        planRepository.getMemoList(pid) {code, memos ->
//            if(code ==0)
//                memoListLiveData.value = memos
//        }
//        planRepository.getParticipantList(pid) {code, participants ->
//            if(code == 0)
//                userListLiveData.value = participants
//        }
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
    val planListLiveData = MutableLiveData<List<Plan>>()
    val friendListLiveData = MutableLiveData<List<User>>()
    val planLiveData = MutableLiveData<Plan>()
    val memoListLiveData = MutableLiveData<List<Memo>>()
    val userListLiveData = MutableLiveData<List<User>>()
    val concatAdapterLiveData = MutableLiveData<Int>(0)
    val syncCalendarEvent = SingleLiveEvent<Event>()

}
package org.sehproject.sss.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.Memo
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.datatype.User
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

    val editPlanEvent = SingleLiveEvent<Plan>()
    val editCompletePlanEvent = SingleLiveEvent<Int>()
    val startDatePickEvent = SingleLiveEvent<Plan>()
    val endDatePickEvent = SingleLiveEvent<Plan>()
    val deletePlanEvent = SingleLiveEvent<Int>()
    val deletePlanCompleteEvent = SingleLiveEvent<Int>()
    val completePlanCompleteEvent = SingleLiveEvent<Int>()
    val completePlanFailEvent = SingleLiveEvent<Int>()
    val kickOutPlanEvent = SingleLiveEvent<Int>()
    val kickOutPlanCompleteEvent = SingleLiveEvent<Int>()
    val cancelPlanEvent = SingleLiveEvent<Int>()
    val cancelPlanCompleteEvent = SingleLiveEvent<Int>()
    val createMemoEvent = SingleLiveEvent<Int>()
    val createMemoCompleteEvent = SingleLiveEvent<Memo>()
    val deleteMemoCompleteEvent = SingleLiveEvent<Any>()
    val createPlanEvent = SingleLiveEvent<Any>()
    val createPlanFailEvent = SingleLiveEvent<Int>()
    val createPlanOcrEvent = SingleLiveEvent<Any>()
    val createPlanOcrFailEvent = SingleLiveEvent<String>()
    val uploadImgEvent = SingleLiveEvent<Any>()
    val createPlanTypeEvent = SingleLiveEvent<Any>()
    val createPlanTypeFailEvent = SingleLiveEvent<String>()
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
    val isLastPlan = SingleLiveEvent<Boolean>()
    val sortEvent = SingleLiveEvent<Any>()
    val selectedPlanUserList = mutableListOf<String>()
    var isInvite: Boolean = true
    var isSorted: Boolean = false
    lateinit var ocrBitmap: Bitmap
    val cancelInvitePlanEvent = SingleLiveEvent<Any>()
    val createMemoFailEvent = SingleLiveEvent<Any>()
    val editPlanFailEvent = SingleLiveEvent<String>()

    fun setFriendList(pid: Int) {
        if(pid == -1)
            friendRepository.getFriendList { _, list ->
                friendListLiveData.value = list
            }
        else
            planRepository.getParticipantList(pid) {code, list ->
                if(code == 0)
                    friendListLiveData.value = list
            }
    }

    fun setPlanList(isCurrent: Boolean, userId: String = UserInfo.userId) {

        planRepository.getPlanList(userId, isCurrent) {code, list ->
            if(code == 0) {
                val mlist = list as MutableList<Plan>
                if (isSorted)
                    planListLiveData.value = planLogic.sortPlanByCategory(mlist!!)
                else
                    planListLiveData.value = planLogic.sortPlanByTime(mlist!!)
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
        setMemoList(pid)
        planRepository.getParticipantList(pid) {code, participants ->
            if(code == 0)
                userListLiveData.value = participants
            else if (code == 1)
                userListLiveData.value = listOf()
        }
    }
    fun setMemoList(pid: Int) {
        planRepository.getMemoList(pid) {code, memos ->
            if(code == 0)
                memoListLiveData.value = memos
            else if(code == 1)
                memoListLiveData.value = listOf()
        }
    }
}
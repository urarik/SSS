package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.datatype.*
import org.sehproject.sss.logic.PlanLogic
import org.sehproject.sss.utils.SingleLiveEvent

class PlanViewModel: ViewModel() {
    val planLogic = PlanLogic(this)

    val editPlanEvent = SingleLiveEvent<Plan>()
    val editCompletePlanEvent = SingleLiveEvent<Int>()
    val deletePlanEvent = SingleLiveEvent<Any>()
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
    val uploadImgEvent = SingleLiveEvent<Any>()
    val createPlanTypeEvent = SingleLiveEvent<Any>()
    val createPlanCompleteEvent = SingleLiveEvent<Int>()
    val viewPlanDetailsEvent = SingleLiveEvent<Any>()
    val makePlanPublicCompleteEvent = SingleLiveEvent<Int>()
    val invitePlanEvent = SingleLiveEvent<Any>()
    val invitePlanCompleteEvent = SingleLiveEvent<Int>()
    val planListLiveData = MutableLiveData<List<SimplePlan>>()
    val friendListLiveData = MutableLiveData<List<User>>()
    val planLiveData = MutableLiveData<Plan>()
    val memoListLiveData = MutableLiveData<List<Memo>>()
    val userList = MutableLiveData<List<User>>()

}
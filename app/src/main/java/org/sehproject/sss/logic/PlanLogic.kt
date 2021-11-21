package org.sehproject.sss.logic

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.*
import org.sehproject.sss.viewmodel.PlanViewModel
import java.text.SimpleDateFormat
import java.util.*

class PlanLogic(val planViewModel: PlanViewModel) {
    private val planRepository = planViewModel.planRepository

    fun onEditPlanClick(plan: Plan) {
        planViewModel.editPlanEvent.value = plan
    }

    fun onGroupClick(parent: AdapterView<out Adapter>, pos: Int, plan: Plan) {
        val group = parent.getItemAtPosition(pos) as Group
        plan.group = group
    }

    fun onEditPlanCompleteClick(plan: Plan) {
        if (plan.pid == null) onCreatePlanDoneClick(plan)
        else {
            planRepository.editPlan(plan) {code ->
             if(code == 0)
                 planViewModel.editCompletePlanEvent.call()
            }
        }
    }
    fun onCreatePlanDoneClick(plan: Plan) {
        planRepository.createPlan(plan) { code ->
            if (code == 0)
                planViewModel.createPlanCompleteEvent.call()
        }
    }

    fun onDeletePlanClick(pid: Int) {
        planViewModel.deletePlanEvent.value = pid
    }
    fun onDatePickClick(plan: Plan, isStart: Boolean) {
        if(isStart) planViewModel.startDatePickEvent.value = plan
        else planViewModel.endDatePickEvent.value = plan
    }
    fun onDeletePlanConfirmClick(pid: Int) {
        planViewModel.planRepository.deletePlan(pid) { code ->
            //TODO()
        }
    }
    fun onDeletePlanRejectClick() {}
    fun onCompletePlanClick(plan: Plan) {
        planRepository.completePlan(plan.pid!!) { code: Int ->
            if(code == 0) {
                planViewModel.completePlanCompleteEvent.call()
            }
        }
    }
    fun onInvitePlanClick() {
        planViewModel.invitePlanEvent.call()
    }
    fun onInvitePlanDoneClick() {}
    fun onInvitePlanExitClick() {}
    fun onKickOutPlanClick() {
        planViewModel.kickOutPlanEvent.call()
    }
    fun onKickOutPlanDoneClick() {}
    fun onKickOutPlanExitClick() {}
    fun onCancelPlanClick() {
        planViewModel.cancelPlanEvent.call()
    }
    fun onCancelPlanConfirmClick() {}
    fun onTrackClick() {}
    fun onCreateMemoClick() {
        planViewModel.createMemoEvent.call()
    }
    fun onCreateMemoDoneClick(memoString: String) {
        var memo = Memo()
        memo.memo = memoString
        memo.pid = planViewModel.planLiveData.value?.pid!!
        memo.writer = User(UserInfo.userId, UserInfo.userName, false, false)

        planRepository.createMemo(memo) { code: Int ->
            if(code == 0) {
                planViewModel.createMemoCompleteEvent.call()
            }
        }
    }
    fun onCreateMemoExitClick() {

    }
    fun onDeleteMemoClick() {

    }
    fun onCreatePlanClick() {
        planViewModel.createPlanEvent.call()
    }
    fun onCreateTypeClick() {
        planViewModel.createPlanTypeEvent.call()
    }
    fun onTypeDoneClick() {
        planViewModel.createPlanCompleteEvent.call()
    }
    fun onPublicPlanClick() {}
    fun onCreateOcrClick() {
        planViewModel.createPlanOcrEvent.call()
    }
    fun onOcrImgClick() {}
    fun onOcrDoneClick() {
        planViewModel.createPlanCompleteEvent.call()
    }
    fun onPreviousPlanListToggle() {}
    @SuppressLint("SimpleDateFormat")
    fun onViewPlanClick(pid: Int, endTime: String) {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormatter.parse(endTime)
        planViewModel.viewPlanDetailsEvent.value = pid
    }
    fun onItemClick(user: User) {
        TODO("Not yet implemented")
    }

}

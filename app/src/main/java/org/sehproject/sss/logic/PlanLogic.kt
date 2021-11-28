package org.sehproject.sss.logic

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Adapter
import android.widget.AdapterView
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventDateTime
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.*
import org.sehproject.sss.utils.StringParser
import org.sehproject.sss.viewmodel.PlanViewModel
import java.text.SimpleDateFormat
import com.google.api.services.calendar.Calendar
import java.util.Collections.list

class PlanLogic(val planViewModel: PlanViewModel) {

    fun onEditPlanClick(plan: Plan) {
        planViewModel.editPlanEvent.value = plan
    }

    fun onGroupClick(parent: AdapterView<out Adapter>, pos: Int, plan: Plan) {
        val group = parent.getItemAtPosition(pos) as Group
        plan.group = group
    }

    fun onEditPlanCompleteClick(plan: Plan) {
        Log.d("TAG", plan.pid.toString())
        if (plan.pid == null) onCreatePlanDoneClick(plan)
        else {
            planViewModel.planRepository.editPlan(plan) {code ->
                Log.d("TAG", code.toString())
             if(code == 0)
                 planViewModel.editCompletePlanEvent.call()
            }
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun onCreatePlanDoneClick(plan: Plan) {
        //테스트용. 테스트 완료 시 craetePlan의 callback에 넣자
        val format = SimpleDateFormat("yyyy-MM-dd hh:mm")

        if(plan.group.name == "그룹 없음") plan.group = Group()
        val start = format.parse(plan.startTime)
        val end = format.parse(plan.endTime)
        if(start <= end) {
            planViewModel.planRepository.createPlan(plan) { code ->
                Log.d("TAG", code.toString())
                if (code == 0) {
                    planViewModel.createPlanCompleteEvent.call()
                }
            }
        } else planViewModel.createPlanFailEvent.value = 1
    }
    fun syncCalendar(mService: Calendar, event: Event) {
        planViewModel.planRepository.syncCalendar(mService, event)
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
            if (code == 0) {
                planViewModel.deletePlanCompleteEvent.call()
            }
        }
    }
    fun onDeletePlanRejectClick() {}
    fun onCompletePlanClick(plan: Plan) {
        planViewModel.planRepository.completePlan(plan.pid!!) { code: Int ->
            if(code == 0) {
                planViewModel.planRepository.addPoint(100) { code: Int ->
                    if(code == 0) {
                        planViewModel.completePlanCompleteEvent.call()
                    }
                }
            }
        }
    }
    fun onInvitePlanClick(pid: Int) {
        planViewModel.is_invite = true
        planViewModel.invitePlanEvent.value = pid
    }
    fun onInvitePlanDoneClick(pid: Int) {
        Log.d("TAG", pid.toString())
        Log.d("TAG", planViewModel.selectedPlanUserList.toString())
        planViewModel.planRepository.invitePlan(pid, planViewModel.selectedPlanUserList) { code: Int ->
            if(code == 0) {
                planViewModel.invitePlanCompleteEvent.call()
            }
        }
    }
    fun onInvitePlanExitClick() {
        planViewModel.invitePlanCompleteEvent.call()
    }
    fun onKickOutPlanClick(pid: Int) {
        planViewModel.is_invite = false
        planViewModel.kickOutPlanEvent.value = pid
    }
    fun onKickOutPlanDoneClick(pid: Int) {
        planViewModel.planRepository.kickOutPlan(pid, planViewModel.selectedPlanUserList) { code: Int ->
            if(code == 0) {
                planViewModel.kickOutPlanCompleteEvent.call()
            }
        }
    }
    fun onKickOutPlanExitClick() {}
    fun onCancelPlanClick(pid: Int) {
        planViewModel.cancelPlanEvent.value = pid
    }
    fun onCancelPlanConfirmClick(pid: Int) {
        planViewModel.planRepository.cancelPlan(pid) { code ->
            if (code == 0) {
                planViewModel.cancelPlanCompleteEvent.call()
            }
        }
    }
    fun onTrackClick() {}
    fun onCreateMemoClick() {
        planViewModel.createMemoEvent.call()
    }
    fun onCreateMemoDoneClick(memoString: String) {
        val memo = Memo()
        memo.memo = memoString
        memo.pid = planViewModel.planLiveData.value?.pid!!

        planViewModel.planRepository.createMemo(memo) { code: Int ->
            Log.d("TAG", code.toString())
            if(code == 0) {
                planViewModel.createMemoCompleteEvent.call()
            }
        }
    }
    fun onCreateMemoExitClick() {}
    fun onDeleteMemoClick(pid: Int) {
        Log.d("TAG", pid.toString())
        planViewModel.planRepository.deleteMemo(pid) { code ->
            if(code == 0) {
                planViewModel.deleteMemoCompleteEvent.call()
            }
        }
    }
    fun onCreatePlanClick() {
        planViewModel.createPlanEvent.call()
    }
    fun onCreateTypeClick() {
        planViewModel.createPlanTypeEvent.call()
    }
    fun onTypeDoneClick(planStr: String) {
        val parser = StringParser()
        val plan = parser.parse(planStr)
        if(plan.pid == -1) planViewModel.createPlanTypeFailEvent.value = plan.name
        else onCreatePlanDoneClick(plan)
    }
    fun onAcceptPlanClick(pid: Int)
    {
        planViewModel.planRepository.acceptPlan(pid, true) { code ->
            if(code == 0)
                planViewModel.acceptPlanInviteEvent.call()
        }
    }

    fun onRefusePlanClick(pid: Int)
    {
        planViewModel.planRepository.acceptPlan(pid, false) { code ->
            if(code == 0)
                planViewModel.refusePlanInviteEvent.call()
        }
    }


    fun onPublicPlanClick(plan: Plan, isChecked: Boolean) {
        Log.d("TAG", "plan: $plan\nisChecked: $isChecked")
        planViewModel.planRepository.setPlanVisibility(plan.pid!!, isChecked) { code ->
            if(code ==0)
                planViewModel.makePlanPublicCompleteEvent.call()
        }
    }
    fun onCreateOcrClick() {
        planViewModel.createPlanOcrEvent.call()
    }
    fun onOcrImgClick() {
        planViewModel.uploadImgEvent.call()
    }
    fun onOcrDoneClick() {
        planViewModel.planRepository.getImageOcr(planViewModel.ocrBitmap) {
                code, string ->
            if(code == 0) {
                Log.d("TAG", string.toString())
                val parser = StringParser()
                val plan = parser.parse(string!!)
                if(plan.pid == -1) planViewModel.createPlanOcrFailEvent.value = plan.name
                else onCreatePlanDoneClick(plan)
            }
        }
    }

    fun onPreviousPlanListToggle(parent: AdapterView<out Adapter>, pos: Int) {

    }
    @SuppressLint("SimpleDateFormat")
    fun onViewPlanClick(pid: Int, endTime: String) {
        planViewModel.viewPlanDetailsEvent.value = pid
    }
    fun onItemClick(user: User) {
        planViewModel.selectedPlanUserList.add(user.userId)
        Log.d("TAG", planViewModel.selectedPlanUserList.toString())
    }

    fun sortPlanByTime(list: List<Plan>): List<Plan> {
        return list.sortedBy { it.startTime }
    }

    fun sortPlanByCategory(list: List<Plan>): List<Plan> {
        return list.sortedWith(compareBy({ it.category }, { it.startTime }))
    }

    fun onLastPlanToggleClick(isCurrentPlan: Boolean) {
        planViewModel.isLastPlan.value = !isCurrentPlan
    }
}

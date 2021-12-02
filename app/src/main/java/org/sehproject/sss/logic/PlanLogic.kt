package org.sehproject.sss.logic

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Adapter
import android.widget.AdapterView
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.Group
import org.sehproject.sss.datatype.Memo
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.datatype.User
import org.sehproject.sss.utils.StringParser
import org.sehproject.sss.viewmodel.PlanViewModel
import java.text.SimpleDateFormat
import java.util.*

class PlanLogic(val planViewModel: PlanViewModel) {

    fun onEditPlanClick(plan: Plan) {
        planViewModel.editPlanEvent.value = plan
    }

    fun onGroupClick(parent: AdapterView<out Adapter>, pos: Int, plan: Plan) {
        val group = parent.getItemAtPosition(pos) as Group
        plan.group = group
    }

    @SuppressLint("SimpleDateFormat")
    fun onEditPlanCompleteClick(plan: Plan) {
        val format = SimpleDateFormat("yyyy-MM-dd hh:mm")
        if(plan.group.name == "그룹 없음") plan.group = Group()
        val start = format.parse(plan.startTime)
        val end = format.parse(plan.endTime)

        if (plan.location.isEmpty())
            planViewModel.editPlanFailEvent.value = "장소를 입력해주세요."
        else if (start >= end)
            planViewModel.editPlanFailEvent.value = "시작 시간이 종료 시간보다 미래입니다."
        else {
            if (plan.pid == null) onCreatePlanDoneClick(plan)
            else {
                planViewModel.planRepository.editPlan(plan) {code ->
                    if(code == 0)
                        planViewModel.editCompletePlanEvent.call()
                }
            }
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun onCreatePlanDoneClick(plan: Plan) {
        val format = SimpleDateFormat("yyyy-MM-dd hh:mm")

        if(plan.group.name == "그룹 없음") plan.group = Group()
        val start = format.parse(plan.startTime)
        val end = format.parse(plan.endTime)
        if(start <= end) {
            planViewModel.planRepository.createPlan(plan) { code ->
                if (code == 0) {
                    Log.d("TAG", plan.toString())
                    planViewModel.createPlanCompleteEvent.call()
                } else
                    planViewModel.createPlanFailEvent.value = code
            }
        } else planViewModel.createPlanFailEvent.value = 1
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
    fun onSortingClick(pos: Int) {
        if (pos == 0 && planViewModel.planListLiveData.value != null) {
            planViewModel.planListLiveData.value =
                planViewModel.planLogic.sortPlanByTime(planViewModel.planListLiveData.value!!)
            planViewModel.sortEvent.call()
            planViewModel.isSorted = false
        } else if (pos == 1&& planViewModel.planListLiveData.value != null) {
            planViewModel.planListLiveData.value =
                planViewModel.planLogic.sortPlanByCategory(planViewModel.planListLiveData.value!!)
            planViewModel.sortEvent.call()
            planViewModel.isSorted = true
        }
    }

    fun onCompletePlanClick(pid: Int, startTime: String) {
        val format = SimpleDateFormat("yyyy-MM-dd hh:mm")
        try {
            val start = format.parse(startTime)
            val now = Calendar.getInstance().time;
            if (start > now) {
                planViewModel.completePlanFailEvent.call()
                return
            }
        } catch(e: Error) {
            Log.e("error!", e.toString())
        }


        planViewModel.planRepository.completePlan(pid) { code: Int ->
            if(code == 0) {
                planViewModel.planRepository.addPoint(100) { code2: Int ->
                    if(code2 == 0) {
                        planViewModel.completePlanCompleteEvent.call()
                    }
                }
            }
        }
    }
    fun onInvitePlanClick(pid: Int) {
        planViewModel.isInvite = true
        planViewModel.invitePlanEvent.value = pid
    }
    fun onInvitePlanDoneClick(pid: Int) {
        planViewModel.planRepository.invitePlan(pid, planViewModel.selectedPlanUserList) { code: Int ->
            if(code == 0) {
                planViewModel.invitePlanCompleteEvent.call()
            }
        }
    }

    fun onKickOutPlanClick(pid: Int) {
        planViewModel.isInvite = false
        planViewModel.kickOutPlanEvent.value = pid
    }
    fun onKickOutPlanDoneClick(pid: Int) {
        planViewModel.planRepository.kickOutPlan(pid, planViewModel.selectedPlanUserList) { code: Int ->
            if(code == 0) {
                planViewModel.kickOutPlanCompleteEvent.call()
            }
        }
    }
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
    fun onCreateMemoClick(pid: Int) {
        planViewModel.createMemoEvent.value = pid
    }
    fun onCreateMemoDoneClick(memoString: String, pid: Int) {
        val memo = Memo()
        memo.memo = memoString
        memo.pid = pid

        if (memoString.length > 100) {
            planViewModel.createMemoFailEvent.call()
        }
        else {
            planViewModel.planRepository.createMemo(memo) { code: Int ->
                if(code == 0) {
                    planViewModel.createMemoCompleteEvent.value = memo.also { it.writer = UserInfo.nickname }
                }
            }
        }
    }
    fun onDeleteMemoClick(pid: Int) {
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


    fun onPublicPlanClick(pid: Int, isChecked: Boolean) {
        planViewModel.planRepository.setPlanVisibility(pid, isChecked) { code ->
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
                val parser = StringParser()
                val plan = parser.parse(string!!)
                if(plan.pid == -1) planViewModel.createPlanOcrFailEvent.value = plan.name
                else onCreatePlanDoneClick(plan)
            }
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun onViewPlanClick(pid: Int) {
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

    fun onInvitePlanCancelClick() {
        planViewModel.cancelInvitePlanEvent.call()
    }
}

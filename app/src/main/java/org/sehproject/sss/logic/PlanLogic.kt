package org.sehproject.sss.logic

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.provider.CalendarContract
import android.util.Log
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventDateTime
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.*
import org.sehproject.sss.utils.StringParser
import org.sehproject.sss.viewmodel.PlanViewModel
import java.text.SimpleDateFormat
import com.google.api.services.calendar.Calendar

class PlanLogic(val planViewModel: PlanViewModel) {

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
            planViewModel.planRepository.editPlan(plan) {code ->
             if(code == 0)
                 planViewModel.editCompletePlanEvent.call()
            }
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun onCreatePlanDoneClick(plan: Plan) {
        //테스트용. 테스트 완료 시 craetePlan의 callback에 넣자
//        val event = Event()
//            .setSummary(plan.name)
//            .setLocation(plan.location)
//        val format = SimpleDateFormat("yyyy-MM-dd hh:mm")
//        val parsedStart = format.parse(plan.startTime)
//        val parsedEnd = format.parse(plan.endTime)
//        val startDate = DateTime(parsedStart)
//        val start = EventDateTime()
//            .setDateTime(startDate)
//            .setTimeZone("Asia/Seoul")
//        val endDate = DateTime(parsedEnd)
//        val end = EventDateTime()
//            .setDate(endDate)
//            .setTimeZone("Asia/Seoul")
//        event.start = start
//        event.end = end
//        planViewModel.syncCalendarEvent.value = event
        
        planViewModel.planRepository.createPlan(plan) { code ->
            if (code == 0) {
                planViewModel.createPlanCompleteEvent.call()
            }
        }
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
            //TODO()
        }
    }
    fun onDeletePlanRejectClick() {}
    fun onCompletePlanClick(plan: Plan) {
        planViewModel.planRepository.completePlan(plan.pid!!) { code: Int ->
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

        planViewModel.planRepository.createMemo(memo) { code: Int ->
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
    fun onTypeDoneClick(planStr: String) {
        val parser = StringParser()
        planViewModel.planRepository.createPlan(parser.parse(planStr)) { code ->
            if(code == 0) {
                planViewModel.createPlanCompleteEvent.call()
            }
        }
    }
    fun onAcceptPlanClick()
    {
        //planViewModel.planRepository.acceptPlan()
        planViewModel.acceptPlanInviteEvent.call()
    }

    fun onRefusePlanClick()
    {
        //planViewModel.planRepository.acceptPlan()
        planViewModel.refusePlanInviteEvent.call()
    }


    fun onPublicPlanClick(plan: Plan, isChecked: Boolean) {
        Log.d("TAG", "plan: $plan\nisChecked: $isChecked")
        planViewModel.planRepository.setPlanVisibility(plan.pid!!, true) { code ->
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
        planViewModel.createPlanCompleteEvent.call()
    }
    fun onPreviousPlanListToggle(parent: AdapterView<out Adapter>, pos: Int) {

    }
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

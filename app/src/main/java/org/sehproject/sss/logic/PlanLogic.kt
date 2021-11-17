package org.sehproject.sss.logic

import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.PlanViewModel

class PlanLogic(val planViewModel: PlanViewModel) {
    fun onEditPlanClick() {
        planViewModel.editPlanEvent.call()
    }
    fun onEditPlanCompleteClick() {
        planViewModel.editCompletePlanEvent.call()
    }
    fun onDeletePlanClick() {
        planViewModel.deletePlanEvent.call()
    }
    fun onDeletePlanConfirmClick() {}
    fun onDeletePlanRejectClick() {}
    fun onCompletePlanClick() {
        planViewModel.completePlanCompleteEvent.call()
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
    fun onCreateMemoDoneClick(memo: String) {}
    fun onCreateMemoExitClick() {}
    fun onDeleteMemoClick() {}
    fun onCreatePlanClick() {
        planViewModel.createPlanEvent.call()
    }
    fun onCreatePlanDoneClick(plan: Plan) {}
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
    fun onViewPlanClick(pid: Int, isPrevious: Boolean) {
        planViewModel.viewPlanDetailsEvent.call()
    }
    fun onItemClick(user: User) {
        TODO("Not yet implemented")
    }

}

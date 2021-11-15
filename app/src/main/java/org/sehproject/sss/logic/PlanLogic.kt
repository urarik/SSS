package org.sehproject.sss.logic

import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.PlanViewModel

class PlanLogic(val planViewModel: PlanViewModel) {
    fun onEditPlanClick() {}
    fun onEditPlanCompleteClick() {}
    fun onDeletePlanClick() {}
    fun onDeletePlanConfirmClick() {}
    fun onDeletePlanRejectClick() {}
    fun onCompletePlanClick() {}
    fun onInvitePlanClick() {}
    fun onInvitePlanDoneClick() {}
    fun onInvitePlanExitClick() {}
    fun onKickOutPlanClick() {}
    fun onKickOutPlanDoneClick() {}
    fun onKickOutPlanExitClick() {}
    fun onCancelPlanClick() {}
    fun onCancelPlanConfirmClick() {}
    fun onTrackClick() {}
    fun onCreateMemoClick() {}
    fun onCreateMemoDoneClick(memo: String) {}
    fun onCreateMemoExitClick() {}
    fun onDeleteMemoClick() {}
    fun onCreatePlanClick() {}
    fun onCreatePlanDoneClick(plan: Plan) {}
    fun onCreateTypeClick() {}
    fun onTypeDoneClick() {}
    fun onPublicPlanClick() {}
    fun onCreateOcrClick() {}
    fun onOcrImgClick() {}
    fun onOcrDoneClick() {}
    fun onPreviousPlanListToggle() {}
    fun onViewPlanClick(pid: Int, isPrevious: Boolean) {}
    fun onItemClick(user: User) {
        TODO("Not yet implemented")
    }

}

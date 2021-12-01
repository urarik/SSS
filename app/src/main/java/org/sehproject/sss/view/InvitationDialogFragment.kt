package org.sehproject.sss

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import org.sehproject.sss.databinding.FragmentInvitationBinding
import org.sehproject.sss.datatype.Invitation
import org.sehproject.sss.viewmodel.GroupViewModel
import org.sehproject.sss.viewmodel.PlanViewModel

class InvitationDialogFragment : DialogFragment() {
        val planViewModel: PlanViewModel by lazy {
            ViewModelProvider(this).get(PlanViewModel::class.java)
        }
        val groupViewModel: GroupViewModel by lazy {
            ViewModelProvider(this).get(GroupViewModel::class.java)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val invitationBinding: FragmentInvitationBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_invitation, container, false)
        val invitation = Invitation(
            requireArguments().getInt("id"),
            requireArguments().getString("invite_type")!!,
            requireArguments().getString("inviter")!!,
            requireArguments().getString("target_name")!!)

        invitationBinding.planlogic = planViewModel.planLogic
        invitationBinding.grouplogic = groupViewModel.groupLogic
        invitationBinding.invitation = invitation

        initObserver()
        return invitationBinding.root
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window!!
        val point = Point()
        window.windowManager.defaultDisplay.getSize(point)

        dialog?.window?.setLayout((point.x * 0.85).toInt(), (point.y * 0.75).toInt())
    }

    private fun initObserver() {
        planViewModel.acceptPlanInviteEvent.observe(viewLifecycleOwner, {
            dismiss()
        })
        planViewModel.refusePlanInviteEvent.observe(viewLifecycleOwner, {
            dismiss()
        })
        groupViewModel.acceptGroupInviteEvent.observe(viewLifecycleOwner, {
            dismiss()
        })
        groupViewModel.refuseGroupInviteEvent.observe(viewLifecycleOwner, {
            dismiss()
        })
    }

}
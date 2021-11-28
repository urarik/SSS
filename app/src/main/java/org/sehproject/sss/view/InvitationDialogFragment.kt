package org.sehproject.sss

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.sehproject.sss.databinding.FragmentInvitationBinding
import org.sehproject.sss.databinding.FragmentPlanDetailBinding
import org.sehproject.sss.datatype.Invitation
import org.sehproject.sss.view.GroupInviteDialogFragmentArgs
import org.sehproject.sss.view.MainActivity
import org.sehproject.sss.viewmodel.FriendViewModel
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
    ): View? {
        // Inflate the layout for this fragment
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
        return inflater.inflate(R.layout.fragment_invitation, container, false)
    }

    private fun initObserver() {
        val navController = findNavController()
        planViewModel.acceptPlanInviteEvent.observe(viewLifecycleOwner, {
            navController.popBackStack()
        })
        planViewModel.refusePlanInviteEvent.observe(viewLifecycleOwner, {
            navController.popBackStack()
        })
        groupViewModel.acceptGroupInviteEvent.observe(viewLifecycleOwner, {
            navController.popBackStack()
        })
        groupViewModel.refuseGroupInviteEvent.observe(viewLifecycleOwner, {
            navController.popBackStack()
        })
    }

}
package org.sehproject.sss.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentPlanInviteBinding
import org.sehproject.sss.view.dialog.DialogFragment
import org.sehproject.sss.viewmodel.PlanViewModel

class PlanInviteDialogFragment: DialogFragment() {
    private val safeArgs: PlanInviteDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        planViewModel = ViewModelProvider(this).get(PlanViewModel::class.java)

        val inviteFriendBinding: FragmentPlanInviteBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_plan_invite, container, false)
        val recyclerView = inviteFriendBinding.searchRecyclerView

        initObserver(recyclerView, safeArgs.pid)

        if(!safeArgs.isInvite)
            inviteFriendBinding.buttonPlanInviteDone.text = "퇴장"
        inviteFriendBinding.isInvite = safeArgs.isInvite
        inviteFriendBinding.planLogic = planViewModel!!.planLogic
        inviteFriendBinding.pid = safeArgs.pid
        if(safeArgs.isInvite)
            planViewModel?.setFriendList(-1)
        else
            planViewModel?.setFriendList(safeArgs.pid)

        return inviteFriendBinding.root
    }

    fun initObserver(recyclerView: RecyclerView, pid: Int) {
        val navController = findNavController()
        planViewModel!!.invitePlanCompleteEvent.observe(viewLifecycleOwner, {
            navController.popBackStack()
        })

        planViewModel!!.kickOutPlanCompleteEvent.observe(viewLifecycleOwner, {
            val action = PlanInviteDialogFragmentDirections.actionPlanInviteDialogFragmentToPlanDetailFragment(pid)
            navController.navigate(action)
        })

        planViewModel?.friendListLiveData?.observe(viewLifecycleOwner, Observer {
            adapter = UserAdapter(it)
            recyclerView.adapter = adapter
        })

        planViewModel!!.cancelInvitePlanEvent.observe(viewLifecycleOwner, Observer {
            navController.popBackStack()
        })
    }
}
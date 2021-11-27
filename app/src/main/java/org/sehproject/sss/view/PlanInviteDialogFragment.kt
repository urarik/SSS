package org.sehproject.sss.view

import android.os.Bundle
import android.util.Log
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
import org.sehproject.sss.databinding.FragmentBanBinding
import org.sehproject.sss.databinding.FragmentInviteFriendBinding
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

//        val inviteFriendBinding: FragmentPlanInviteBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_plan_invite, container, false)
//        val recyclerView = inviteFriendBinding.searchRecyclerView
//        if(!safeArgs.isInvite)
//            inviteFriendBinding.buttonPlanInviteDone.text = "퇴장"
//        inviteFriendBinding.isInvite = safeArgs.isInvite
//        inviteFriendBinding.planLogic = planViewModel!!.planLogic

        var inviteFriendBinding: FragmentInviteFriendBinding? = null
        var kickOutFriendBinding: FragmentBanBinding? = null
        var view: View
        var recyclerView: RecyclerView

        if (safeArgs.isInvite) {
            inviteFriendBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.fragment_invite_friend, container, false)
            view = inviteFriendBinding.root
            recyclerView = inviteFriendBinding.searchRecyclerView
            inviteFriendBinding.planLogic = planViewModel!!.planLogic
        } else {
            kickOutFriendBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.fragment_ban, container, false)
            view = kickOutFriendBinding.root
            recyclerView = kickOutFriendBinding.searchRecyclerView
            kickOutFriendBinding.planLogic = planViewModel!!.planLogic
        }

        planViewModel?.setFriendList()
        initObserver(recyclerView)

        // return inviteFriendBinding.root
        return view
    }

    fun initObserver(recyclerView: RecyclerView) {
        val navController = findNavController()
        planViewModel!!.invitePlanCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.planDetailFragment)
        })
        planViewModel!!.kickOutPlanCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.planDetailFragment)
        })
        planViewModel?.friendListLiveData?.observe(viewLifecycleOwner, Observer {
            adapter = UserAdapter(it)
            recyclerView.adapter = adapter
        })
    }
}
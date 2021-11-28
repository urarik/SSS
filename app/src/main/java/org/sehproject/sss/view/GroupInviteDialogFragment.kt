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
import org.sehproject.sss.databinding.FragmentGroupInviteBinding
import org.sehproject.sss.view.dialog.DialogFragment
import org.sehproject.sss.viewmodel.GroupViewModel
import org.sehproject.sss.viewmodel.UserViewModel

class GroupInviteDialogFragment: DialogFragment() {
    private val safeArgs: GroupInviteDialogFragmentArgs by navArgs() //gid, is_invite

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)

        val inviteFriendBinding: FragmentGroupInviteBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_group_invite, container, false)
        val recyclerView = inviteFriendBinding.searchRecyclerView
        if(!safeArgs.isInvite)
            inviteFriendBinding.buttonGroupInviteDone.text = "퇴장"
        inviteFriendBinding.isInvite = safeArgs.isInvite
        inviteFriendBinding.gid = safeArgs.gid
        inviteFriendBinding.groupLogic = groupViewModel!!.groupLogic

        initObserver(recyclerView)

        groupViewModel?.setFriendList()

        return inviteFriendBinding.root
    }

    fun initObserver(recyclerView: RecyclerView) {
        val navController = findNavController()
        groupViewModel!!.inviteGroupCompleteEvent.observe(viewLifecycleOwner, {
            navController.popBackStack()
        })
        groupViewModel!!.kickOutGroupCompleteEvent.observe(viewLifecycleOwner, {
            val action = GroupInviteDialogFragmentDirections.actionGroupInviteFragmentToGroupDetailFragment(safeArgs.gid)
            navController.navigate(action)
        })
        groupViewModel?.friendListLiveData?.observe(viewLifecycleOwner, Observer {
            adapter = UserAdapter(it)
            recyclerView.adapter = adapter
        })
    }
}
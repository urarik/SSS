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
import org.sehproject.sss.view.dialog.DialogFragment
import org.sehproject.sss.viewmodel.GroupViewModel
import org.sehproject.sss.viewmodel.UserViewModel

class GroupInviteDialogFragment: DialogFragment() {
    private val safeArgs: GroupInviteDialogFragmentArgs by navArgs() //groupid, is_invite

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        var inviteFriendBinding: FragmentInviteFriendBinding? = null
        var kickOutFriendBinding: FragmentBanBinding? = null
        var view: View
        var recyclerView: RecyclerView

        if (safeArgs.isInvite) {
            inviteFriendBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.fragment_invite_friend, container, false)
            view = inviteFriendBinding.root
            recyclerView = inviteFriendBinding.searchRecyclerView
        } else {
            kickOutFriendBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.fragment_ban, container, false)
            view = kickOutFriendBinding.root
            recyclerView = kickOutFriendBinding.searchRecyclerView
        }

        groupViewModel?.setFriendList()
        groupViewModel?.friendListLiveData?.observe(viewLifecycleOwner, Observer {
            adapter = UserAdapter(it)
            recyclerView.adapter = adapter
        })

        initObserver()

        return view
    }

    fun initObserver() {
        val navController = findNavController()
        groupViewModel!!.inviteGroupCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.groupDetailFragment)
        })
        groupViewModel!!.kickOutGroupCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.groupDetailFragment)
        })
    }
}
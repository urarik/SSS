package org.sehproject.sss.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentInviteFriendBinding
import org.sehproject.sss.view.dialog.DialogFragment
import org.sehproject.sss.viewmodel.PlanViewModel

class PlanInviteDialogFragment: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        planViewModel = ViewModelProvider(this).get(PlanViewModel::class.java)
        val inviteFriendBinding: FragmentInviteFriendBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_invite_friend, container, false)
        val view = inviteFriendBinding.root

        val recyclerView: RecyclerView = inviteFriendBinding.searchRecyclerView
        planViewModel?.setFriendList()
        planViewModel?.friendListLiveData?.observe(viewLifecycleOwner, Observer {
            adapter = UserAdapter(it)
            recyclerView.adapter = adapter
        })
        planViewModel?.setFriendList()

        return view
    }
}
package org.sehproject.sss.view.Friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentFriendListBinding
import org.sehproject.sss.viewmodel.FriendViewModel

class FriendListFragment : Fragment() {
    private val friendViewModel: FriendViewModel by lazy {
        ViewModelProvider(this).get(FriendViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val friendListBinding: FragmentFriendListBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_friend_list, container, false)
        friendListBinding.friendLogic = friendViewModel.friendLogic

        initObserver()

        return friendListBinding.root
    }

    private fun initObserver() {
        friendViewModel.searchUserEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.userSearchFragment)
        })
    }
}
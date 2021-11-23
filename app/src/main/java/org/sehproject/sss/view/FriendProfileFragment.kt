package org.sehproject.sss.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.FragmentMapBinding
import org.sehproject.sss.logic.FriendLogic
import org.sehproject.sss.viewmodel.FriendViewModel


class FriendProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val friendViewModel: FriendViewModel by lazy {
            ViewModelProvider(this).get(FriendViewModel::class.java)
        }
        val friendProfileBinding: FragmentFriendProfileBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_friend_profile,
            container,
            false
        )
        friendProfileBinding.friendLogic = friendViewModel.friendLogic

        return friendProfileBinding.root
    }

}
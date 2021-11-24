package org.sehproject.sss.view

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import org.sehproject.sss.R
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.logic.FriendLogic
import org.sehproject.sss.utils.ProfileViewModelFactory
import org.sehproject.sss.viewmodel.FriendViewModel
import org.sehproject.sss.viewmodel.ProfileViewModel


class FriendProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, ProfileViewModelFactory(appDatabase)).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val friendProfileBinding: FragmentFriendProfileBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_friend_profile,
            container,
            false
        )
        friendProfileBinding.profileLogic = profileViewModel.profileLogic

        val safeArgs: FriendProfileFragmentArgs by navArgs()
        profileViewModel.setProfile(safeArgs.userId)
        Log.d("TAG", safeArgs.userId)

        initObserver(friendProfileBinding)

        return friendProfileBinding.root
    }

    fun initObserver(friendProfileBinding: FragmentFriendProfileBinding) {
        profileViewModel.profileLiveData.observe(viewLifecycleOwner, {
            friendProfileBinding.profile = it
        })
    }
}
package org.sehproject.sss.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.FragmentGroupDetailBinding
import org.sehproject.sss.datatype.Group
import org.sehproject.sss.viewmodel.FriendViewModel
import org.sehproject.sss.viewmodel.GroupViewModel

class GroupDetailFragment : Fragment() {
    private val groupViewModel: GroupViewModel by lazy {
        ViewModelProvider(this).get(GroupViewModel::class.java)
    }
    private val safeArgs: GroupDetailFragmentArgs by navArgs() //gid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val groupDetailBinding: FragmentGroupDetailBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_group_detail,
            container,
            false
        )
        groupDetailBinding.groupLogic = groupViewModel.groupLogic
        groupDetailBinding.group = Group(10, name="GGG")
        initObserver()
        return groupDetailBinding.root
    }

    private fun initObserver() {
        val navController = findNavController()
        groupViewModel.editGroupEvent.observe(this, {
            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupEditFragment(it)
            navController.navigate(action)
        })
        groupViewModel.inviteGroupEvent.observe(this, {
            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupInviteFragment(it)
            navController.navigate(action)
        })
        groupViewModel.kickOutGroupEvent.observe(this, {
            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupKickOutFragment(it)
            navController.navigate(action)
        })
    }

}
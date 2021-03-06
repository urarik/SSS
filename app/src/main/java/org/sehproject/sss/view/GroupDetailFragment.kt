package org.sehproject.sss.view

import android.content.DialogInterface
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.UserInfo
import org.sehproject.sss.databinding.FragmentGroupDetailBinding
import org.sehproject.sss.databinding.ItemParticipantsBinding
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.GroupViewModel

class GroupDetailFragment : Fragment() {
    private val groupViewModel: GroupViewModel by lazy {
        ViewModelProvider(this).get(GroupViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
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
        val safeArgs: GroupDetailFragmentArgs by navArgs()
        groupDetailBinding.groupLogic = groupViewModel.groupLogic

        initObserver(groupDetailBinding)

        groupViewModel.setGroup(safeArgs.gid)
        groupDetailBinding.textViewMemberNumDetails.text = groupViewModel.groupLiveData.value?.participants?.size.toString() + "명"

        return groupDetailBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun initObserver(groupDetailBinding: FragmentGroupDetailBinding) {
        val navController = findNavController()
        groupViewModel.editGroupEvent.observe(viewLifecycleOwner, {
            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupEditFragment(it)
            navController.navigate(action)
        })
        groupViewModel.inviteGroupEvent.observe(viewLifecycleOwner, {
            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupInviteFragment(it, true)
            navController.navigate(action)
        })
        groupViewModel.kickOutGroupEvent.observe(viewLifecycleOwner, {
            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupInviteFragment(it, false)
            navController.navigate(action)
        })
        groupViewModel.exitGroupCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_groupDetailFragment_to_groupListFragment)
        })
        groupViewModel.deleteGroupCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_groupDetailFragment_to_groupListFragment)
        })

        groupViewModel.groupLiveData.observe(viewLifecycleOwner, {
            groupDetailBinding.group = it
            val view = groupDetailBinding.imageViewGroupColor
            view.background.colorFilter = BlendModeColorFilter(
                Color.parseColor("#"+Integer.toHexString(it.color)),
                BlendMode.SRC_ATOP)

            if(groupViewModel.groupLiveData.value?.creator == UserInfo.userId) {
                groupDetailBinding.editGroupFloatingActionButton.visibility = ViewGroup.VISIBLE
                groupDetailBinding.deleteGroupFloatingActionButton.visibility = ViewGroup.VISIBLE
                groupDetailBinding.kickOutGroupFloatingActionButton.visibility = ViewGroup.VISIBLE
                groupDetailBinding.exitGroupFloatingActionButton.visibility = ViewGroup.GONE
            }
        })
        groupViewModel.friendListLiveData.observe(viewLifecycleOwner, {
            groupDetailBinding.textViewMemberNumDetails.text = it.size.toString()
            // groupDetailBinding.recyclerGroupParticipants.adapter = ParticipantAdapter(it)
        })
        groupViewModel.deleteGroupEvent.observe(viewLifecycleOwner, {
            android.app.AlertDialog
                .Builder(context)
                .setMessage("그룹을 삭제하시겠습니까?")
                .setPositiveButton("네") { dialogInterface: DialogInterface, i: Int ->
                    groupViewModel.groupLogic.onDeleteGroupConfirmClick(it)
                }
                .setNegativeButton("아니오", null)
                .show()

        })

    }

    private inner class ParticipantHolder(val itemParticipantBinding: ItemParticipantsBinding)
        : RecyclerView.ViewHolder(itemParticipantBinding.root) {
        fun bind(user: User) {
            itemParticipantBinding.user = user
        }
    }

    private inner class ParticipantAdapter(val users: List<User>) :
        RecyclerView.Adapter<ParticipantHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantHolder {
            val itemParticipantBinding = ItemParticipantsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ParticipantHolder(itemParticipantBinding)
        }

        override fun getItemCount(): Int = users.size

        override fun onBindViewHolder(holder: ParticipantHolder, position: Int) {
            val user = users[position]
            holder.bind(user)
        }
    }

}
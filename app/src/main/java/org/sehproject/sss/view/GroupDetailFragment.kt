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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.FragmentGroupDetailBinding
import org.sehproject.sss.databinding.ItemParticipantsBinding
import org.sehproject.sss.databinding.ItemPlanBinding
import org.sehproject.sss.datatype.Group
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.FriendViewModel
import org.sehproject.sss.viewmodel.GroupViewModel

class GroupDetailFragment : Fragment() {
    private val groupViewModel: GroupViewModel by lazy {
        ViewModelProvider(this).get(GroupViewModel::class.java)
    }

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
        initObserver(groupDetailBinding)

        val safeArgs: GroupDetailFragmentArgs by navArgs()
        groupViewModel.setGroup(safeArgs.gid)
        return groupDetailBinding.root
    }

    private fun initObserver(groupDetailBinding: FragmentGroupDetailBinding) {
        val navController = findNavController()
        groupViewModel.editGroupEvent.observe(viewLifecycleOwner, {
            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupEditFragment(it)
            navController.navigate(action)
        })
        groupViewModel.inviteGroupEvent.observe(viewLifecycleOwner, {
            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupInviteFragment(it)
            navController.navigate(action)
        })
        groupViewModel.kickOutGroupEvent.observe(viewLifecycleOwner, {
            val action = GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupKickOutFragment(it)
            navController.navigate(action)
        })
        groupViewModel.groupLiveData.observe(viewLifecycleOwner, {
            groupDetailBinding.group = it
            groupDetailBinding.textViewMemberNumDetails.text = it.participants?.size.toString()
            groupDetailBinding.recyclerGroupParticipants.adapter = it.participants?.let { it2 ->
                ParticipantAdapter(it2)
            }
            val view = groupDetailBinding.imageViewGroupColor
            view.background.colorFilter = BlendModeColorFilter(
                Color.parseColor("#"+Integer.toHexString(it.color)),
                BlendMode.SRC_ATOP)

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
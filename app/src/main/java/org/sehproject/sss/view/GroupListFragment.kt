package org.sehproject.sss.view

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentGroupListBinding
import org.sehproject.sss.databinding.ItemGroupBinding
import org.sehproject.sss.datatype.Group
import org.sehproject.sss.viewmodel.GroupViewModel

class GroupListFragment : Fragment() {
    private val groupViewModel: GroupViewModel by lazy {
        ViewModelProvider(this).get(GroupViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val groupListBinding: FragmentGroupListBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_group_list,
            container,
            false
        )
        groupListBinding.groupLogic = groupViewModel.groupLogic

        initObserver(groupListBinding)

        groupViewModel.setGroupList()

        val data = resources.getStringArray(R.array.groupOrder)
        val spinnerAdapter = ArrayAdapter(activity as MainActivity, android.R.layout.simple_list_item_1, data)
        groupListBinding.spinnerGroupOrder.adapter = spinnerAdapter

        return groupListBinding.root
    }

    private fun initObserver(groupListBinding: FragmentGroupListBinding) {
        val navController = findNavController()
        groupViewModel.createGroupEvent.observe(viewLifecycleOwner, {
            val action = GroupListFragmentDirections.actionGroupListFragmentToGroupEditFragment(
                Group()
            )
            navController.navigate(action)
        })
        groupViewModel.groupListLiveData.observe(viewLifecycleOwner, {
            val adapter = GroupAdapter(it)
            groupListBinding.RecyclerViewGroupList.adapter = adapter
        })

        groupViewModel.sortEvent.observe(viewLifecycleOwner, {
            val adapter = GroupAdapter(groupViewModel.groupListLiveData.value!!)
            groupListBinding.RecyclerViewGroupList.adapter = adapter
        })
        groupViewModel.viewGroupDetailsEvent.observe(this, {
            val action = GroupListFragmentDirections.actionGroupListFragmentToGroupDetailFragment(it)
            navController.navigate(action)
        })
    }

    private inner class GroupHolder(private val itemGroupBinding: ItemGroupBinding) : RecyclerView.ViewHolder(itemGroupBinding.root) {
        @RequiresApi(Build.VERSION_CODES.Q)
        fun bind(group: Group) {
            itemGroupBinding.group = group
            itemGroupBinding.groupLogic = groupViewModel.groupLogic
            if(group.color != 0) {
                val view = itemGroupBinding.imageGroupColor
                view.background.colorFilter = BlendModeColorFilter(
                    Color.parseColor("#" + Integer.toHexString(group.color)),
                    BlendMode.SRC_ATOP
                )
            }
        }
    }
    private inner class GroupAdapter(private val Groups: List<Group>) :
        RecyclerView.Adapter<GroupHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
            val itemGroupBinding = ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return GroupHolder(itemGroupBinding)
        }

        override fun getItemCount(): Int = Groups.size

        @RequiresApi(Build.VERSION_CODES.Q)
        override fun onBindViewHolder(holder: GroupHolder, position: Int) {
            val group = Groups[position]
            holder.bind(group)
        }

    }

}
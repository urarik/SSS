package org.sehproject.sss.view

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
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
    ): View? {
        val groupListBinding: FragmentGroupListBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_group_list,
            container,
            false
        )
        groupListBinding.groupLogic = groupViewModel.groupLogic
        groupListBinding.spinnerGroupOrder

        groupViewModel.setGroupList()
        initObserver(groupListBinding)

        val data = resources.getStringArray(R.array.groupOrder)
        val spinnerAdapter = ArrayAdapter(activity as MainActivity, android.R.layout.simple_list_item_1, data)
        groupListBinding.spinnerGroupOrder.adapter = spinnerAdapter

        groupListBinding.spinnerGroupOrder.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0 && groupViewModel.groupListLiveData.value != null) {
                    groupViewModel.groupListLiveData.value =
                        groupViewModel.groupLogic.sortGroupByName(groupViewModel.groupListLiveData.value!!)
                    val adapter = GroupAdapter(groupViewModel.groupListLiveData.value!!)
                    groupListBinding.RecyclerViewGroupList.adapter = adapter
                } else if (p2 == 1&& groupViewModel.groupListLiveData.value != null) {
                    groupViewModel.groupListLiveData.value =
                        groupViewModel.groupLogic.sortGroupByMembers(groupViewModel.groupListLiveData.value!!)
                    val adapter = GroupAdapter(groupViewModel.groupListLiveData.value!!)
                    groupListBinding.RecyclerViewGroupList.adapter = adapter
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

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

        val buttonMap = groupListBinding.btnMapTest
        buttonMap?.setOnClickListener {
            findNavController().navigate(R.id.mapFragment, null)
         }
        groupViewModel.viewGroupDetailsEvent.observe(this, {
            Log.d("TAG", "gid: $it")
            val action = GroupListFragmentDirections.actionGroupListFragmentToGroupDetailFragment(it)
            navController.navigate(action)
        })
    }

    protected inner class GroupHolder(private val itemGroupBinding: ItemGroupBinding) : RecyclerView.ViewHolder(itemGroupBinding.root) {

        @RequiresApi(Build.VERSION_CODES.Q)
        fun bind(group: Group) {
            itemGroupBinding.group = group
            itemGroupBinding.groupLogic = groupViewModel.groupLogic
            val view = itemGroupBinding.imageGroupColor
            view.background.colorFilter = BlendModeColorFilter(Color.parseColor("#"+Integer.toHexString(group.color)),
                BlendMode.SRC_ATOP)
            //itemGroupBinding.imageGroupColor.setBackgroundColor(group.color)
        }
    }

    protected inner class GroupAdapter(val Groups: List<Group>) :
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
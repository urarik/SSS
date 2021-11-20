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
import android.widget.Button
import androidx.core.view.marginBottom
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
        //리사이클러뷰 추가할 때 각 아이템에 대해 click listener 달아줘야함.
        //xml 파일에서 가능할거임. 모르면 말하고

        groupViewModel.getGroupList()
        initObserver(groupListBinding.RecyclerViewGroupList)
        return groupListBinding.root
    }

    private fun initObserver(recyclerView: RecyclerView) {
        val navController = findNavController()
        groupViewModel.createGroupEvent.observe(viewLifecycleOwner, {
            val action = GroupListFragmentDirections.actionGroupListFragmentToGroupEditFragment(
                Group()
            )
            navController.navigate(action)
        })
        groupViewModel.groupListLiveData.observe(viewLifecycleOwner, {
            val adapter = GroupAdapter(it)
            recyclerView.adapter = adapter

        })

        //val buttonMap = view.findViewById<Button>(R.id.btn_map_test)
        //buttonMap?.setOnClickListener {
        //    findNavController().navigate(R.id.mapFragment, null)
        // }
        groupViewModel.viewGroupDetailsEvent.observe(viewLifecycleOwner, {
            Log.d("TAG", it.toString())
        })
        groupViewModel.viewGroupDetailsEvent.observe(this, {
            Log.d("TAG", "gid: $it")
            val action = GroupListFragmentDirections.actionGroupListFragmentToGroupDetailFragment(it)
            navController.navigate(action)
        })
    }

    protected inner class GroupHolder(private val itemGroupBinding: ItemGroupBinding) : RecyclerView.ViewHolder(itemGroupBinding.root) {

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

        override fun onBindViewHolder(holder: GroupHolder, position: Int) {
            val group = Groups[position]
            holder.bind(group)
        }

    }

}
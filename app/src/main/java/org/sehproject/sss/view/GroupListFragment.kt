package org.sehproject.sss.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentGroupListBinding
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
        initObserver(groupListBinding.root)
        return groupListBinding.root
    }

    // test 버튼 필요 없을 때 view 삭제
    private fun initObserver(view: View) {
        val navController = findNavController()
        groupViewModel.createGroupEvent.observe(this, {
            val action = GroupListFragmentDirections.actionGroupListFragmentToGroupEditFragment(
                Group()
            )
            navController.navigate(action)
        })

        val buttonMap = view.findViewById<Button>(R.id.btn_map_test)
        buttonMap?.setOnClickListener {
            findNavController().navigate(R.id.mapFragment, null)
        }

        groupViewModel.viewGroupDetailsEvent.observe(this, {
            val action = GroupListFragmentDirections.actionGroupListFragmentToGroupDetailFragment(it)
            navController.navigate(action)
        })
    }

}
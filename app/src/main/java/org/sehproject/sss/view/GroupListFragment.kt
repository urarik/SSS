package org.sehproject.sss.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentGroupListBinding

class GroupListFragment : Fragment() {

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

        initObserver(groupListBinding.root)
        return groupListBinding.root
    }

    private fun initObserver(view: View) {

        val button = view.findViewById<Button>(R.id.buttonMakeGroupList)
        button?.setOnClickListener {
            findNavController().navigate(R.id.groupEditFragment, null)
        }

        val buttonMap = view.findViewById<Button>(R.id.btn_map_test)
        buttonMap?.setOnClickListener {
            findNavController().navigate(R.id.mapFragment, null)
        }

        val buttonDetail = view.findViewById<Button>(R.id.btn_group_detail)
        buttonDetail?.setOnClickListener {
            findNavController().navigate(R.id.groupDetailFragment, null)
        }
    }

}
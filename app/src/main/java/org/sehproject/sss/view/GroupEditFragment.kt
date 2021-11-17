package org.sehproject.sss.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentGroupEditBinding
import org.sehproject.sss.databinding.FragmentInviteFriendBinding
import org.sehproject.sss.datatype.Group
import org.sehproject.sss.viewmodel.GroupViewModel

class GroupEditFragment : Fragment() {
    private val groupViewModel by lazy {
        ViewModelProvider(this).get(GroupViewModel::class.java)
    }
    private val safeArgs: GroupEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val editGroupBinding: FragmentGroupEditBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_group_edit, container, false)
        val view = editGroupBinding.root
        editGroupBinding.groupLogic = groupViewModel.groupLogic
        editGroupBinding.group = Group(name="GG")
        initObserver()
        Log.d("TAG", "group! ${safeArgs.group}")
        return view
    }
    private fun initObserver() {
        groupViewModel.inviteGroupEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.groupInviteFragment)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.buttonMakeGroup)
        button?.setOnClickListener {
            findNavController().navigate(R.id.action_groupEditFragment_to_groupListFragment, null)
        }
    }
}
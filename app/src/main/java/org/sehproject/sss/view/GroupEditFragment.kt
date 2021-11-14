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
import org.sehproject.sss.databinding.FragmentGroupEditBinding
import org.sehproject.sss.databinding.FragmentInviteFriendBinding
import org.sehproject.sss.viewmodel.GroupViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditGroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupEditFragment : Fragment() {
    private val groupViewModel by lazy {
        ViewModelProvider(this).get(GroupViewModel::class.java)
    }


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val editGroupBinding: FragmentGroupEditBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_group_edit, container, false)
        val view = editGroupBinding.root
        editGroupBinding.groupLogic = groupViewModel.groupLogic
        initObserver()

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditGroupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupEditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
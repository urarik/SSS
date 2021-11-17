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
import org.sehproject.sss.databinding.FragmentFriendListBinding
import org.sehproject.sss.databinding.FragmentUserSearchBinding
import org.sehproject.sss.viewmodel.FriendViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserSearchFragment : Fragment() {
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
        val friendViewModel: FriendViewModel by lazy {
            ViewModelProvider(this).get(FriendViewModel::class.java)
        }

        val userSearchBinding: FragmentUserSearchBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_user_search, container, false)
        userSearchBinding.friendLogic = friendViewModel.friendLogic
        val view = userSearchBinding.root

        return view
    }
}
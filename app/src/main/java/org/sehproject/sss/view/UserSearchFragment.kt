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

class UserSearchFragment : Fragment() {
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
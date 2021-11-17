package org.sehproject.sss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentFriendListBinding
import org.sehproject.sss.databinding.FragmentRegisterBinding
import org.sehproject.sss.viewmodel.FriendViewModel
import org.sehproject.sss.viewmodel.UserViewModel

class RegisterFragment : Fragment() {
    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val registerBinding: FragmentRegisterBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_register, container, false)
        registerBinding.userLogic = userViewModel.userLogic

        initObserver()

        return view
    }

    private fun initObserver() {
        userViewModel.registerCompleteEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.loginFragment)
        })
    }
}
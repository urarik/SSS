package org.sehproject.sss

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentRegisterBinding
import org.sehproject.sss.utils.UserViewModelFactory
import org.sehproject.sss.viewmodel.UserViewModel

class RegisterFragment : Fragment() {
    private val userViewModel: UserViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, UserViewModelFactory(appDatabase)).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val registerBinding: FragmentRegisterBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_register, container, false)
        registerBinding.userLogic = userViewModel.userLogic
        val view = registerBinding.root

        initObserver()

        return view
    }

    private fun initObserver() {
        val navController = findNavController()
        userViewModel.registerCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        })
    }
}
package org.sehproject.sss

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    ): View {
        val registerBinding: FragmentRegisterBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_register, container, false)
        val safeArgs: RegisterFragmentArgs by navArgs()

        registerBinding.userLogic = userViewModel.userLogic
        registerBinding.apiId = safeArgs.id

        if (registerBinding.apiId != "") {
            registerBinding.editRegisterPassword.visibility = View.GONE
            registerBinding.editRegisterConfirmPassword.visibility = View.GONE
        }

        initObserver()

        return registerBinding.root
    }

    private fun initObserver() {
        val navController = findNavController()
        userViewModel.registerCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        })
        userViewModel.registerFailEvent.observe(viewLifecycleOwner, {
            Toast.makeText(context, "아이디가 중복됩니다.", Toast.LENGTH_SHORT).show()
        })
    }
}
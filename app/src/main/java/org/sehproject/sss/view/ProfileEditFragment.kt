package org.sehproject.sss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.FragmentProfileEditBinding
import org.sehproject.sss.utils.ProfileViewModelFactory
import org.sehproject.sss.view.GroupDetailFragmentArgs
import org.sehproject.sss.viewmodel.PlanViewModel
import org.sehproject.sss.viewmodel.ProfileViewModel

class ProfileEditFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, ProfileViewModelFactory(appDatabase)).get(ProfileViewModel::class.java)
    }
    private val safeArgs: ProfileEditFragmentArgs by navArgs() //profile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profileEditBinding: FragmentProfileEditBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_profile_edit,
            container,
            false
        )
        profileEditBinding.profileLogic = profileViewModel.profileLogic

        initObserver()
        return profileEditBinding.root
    }

    private fun initObserver() {
        val navController = findNavController()
        profileViewModel.editProfileCompleteEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_profileEditFragment_to_profileFragment)
        })
    }

}
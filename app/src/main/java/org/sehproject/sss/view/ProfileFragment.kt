package org.sehproject.sss

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.FragmentProfileBinding
import org.sehproject.sss.datatype.Profile
import org.sehproject.sss.utils.ProfileViewModelFactory
import org.sehproject.sss.view.MainActivity
import org.sehproject.sss.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, ProfileViewModelFactory(appDatabase)).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profileBinding: FragmentProfileBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_profile,
            container,
            false
        )
        profileBinding.profileLogic = profileViewModel.profileLogic
        profileBinding.profile = Profile()
        profileViewModel.setProfile(UserInfo.userId)
        initObserver()
        return profileBinding.root
    }

    fun initObserver() {
        val navController = findNavController()
        profileViewModel.editProfileEvent.observe(viewLifecycleOwner, {
            val action = ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment(it)
            navController.navigate(action)
        })

        profileViewModel.selectOptionEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.settingFragment)
        })

        profileViewModel.viewStatisticsEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.statisticsDialogFragment)
        })

        profileViewModel.logoutEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_profileFragment_to_loginFragment)
        })
    }
}
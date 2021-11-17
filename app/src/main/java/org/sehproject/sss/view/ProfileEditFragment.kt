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
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.FragmentProfileEditBinding
import org.sehproject.sss.viewmodel.PlanViewModel
import org.sehproject.sss.viewmodel.ProfileViewModel

class ProfileEditFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

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

        initObserver(profileEditBinding.root)
        return profileEditBinding.root
    }

    private fun initObserver(view: View) {
        profileViewModel.editProfileCompleteEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.action_profileEditFragment_to_profileFragment)
        })
    }

}
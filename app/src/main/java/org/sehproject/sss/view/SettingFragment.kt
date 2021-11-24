package org.sehproject.sss

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import org.sehproject.sss.R
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.FragmentSettingBinding
import org.sehproject.sss.utils.ProfileViewModelFactory
import org.sehproject.sss.viewmodel.PlanViewModel
import org.sehproject.sss.viewmodel.ProfileViewModel

class SettingFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, ProfileViewModelFactory(appDatabase)).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val settingBinding: FragmentSettingBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_setting,
            container,
            false
        )
        settingBinding.profileLogic = profileViewModel.profileLogic

        profileViewModel.setOption()
        initObserver(settingBinding)

        return settingBinding.root
    }

    fun initObserver(settingBinding: FragmentSettingBinding) {
        profileViewModel.optionLiveData.observe(viewLifecycleOwner, {
            settingBinding.option = it
        })
    }
}
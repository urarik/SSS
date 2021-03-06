package org.sehproject.sss

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentSettingBinding
import org.sehproject.sss.utils.ProfileViewModelFactory
import org.sehproject.sss.viewmodel.ProfileViewModel

class SettingFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, ProfileViewModelFactory(appDatabase)).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingBinding: FragmentSettingBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_setting,
            container,
            false
        )
        settingBinding.profileLogic = profileViewModel.profileLogic

        initObserver(settingBinding)
        profileViewModel.setOption()

        return settingBinding.root
    }

    fun initObserver(settingBinding: FragmentSettingBinding) {
        profileViewModel.optionLiveData.observe(viewLifecycleOwner, {
            settingBinding.option = it
        })
    }
}
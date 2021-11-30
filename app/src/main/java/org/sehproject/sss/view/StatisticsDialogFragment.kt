package org.sehproject.sss.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.R
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentStatisticsPointBinding
import org.sehproject.sss.utils.ProfileViewModelFactory
import org.sehproject.sss.viewmodel.ProfileViewModel

class StatisticsDialogFragment: DialogFragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, ProfileViewModelFactory(appDatabase)).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val statisticsBinding: FragmentStatisticsPointBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_statistics_point, container, false)
        statisticsBinding.profileLogic = profileViewModel.profileLogic

        initObserver(statisticsBinding)

        profileViewModel.setStatistics()

        return statisticsBinding.root
    }

    fun initObserver(statisticsBinding: FragmentStatisticsPointBinding) {
        val navController = findNavController()
        profileViewModel.viewStatisticsExitEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_statisticsDialogFragment_to_profileFragment)
        })

        profileViewModel.getStatisticsCompleteEvent.observe(viewLifecycleOwner, {
            statisticsBinding.statistics = profileViewModel.statisticsLiveData.value
            statisticsBinding.profileViewModel = profileViewModel
        })
    }
}
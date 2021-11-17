package org.sehproject.sss.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentPlanCreateOcrBinding
import org.sehproject.sss.databinding.FragmentPlanDetailBinding
import org.sehproject.sss.viewmodel.MapViewModel
import org.sehproject.sss.viewmodel.PlanViewModel

class PlanDetailFragment : Fragment() {
    val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }
    val mapViewModel: MapViewModel by lazy {
        ViewModelProvider(this).get(MapViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val planDetailBinding: FragmentPlanDetailBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_plan_detail, container, false)
        planDetailBinding.planLogic = planViewModel.planLogic
        val view = planDetailBinding.root

        initObserver()

        return view
    }

    fun initObserver() {
        planViewModel.editPlanEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.planEditFragment)
        })

        planViewModel.kickOutPlanEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.groupInviteFragment)
        })

        planViewModel.invitePlanEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.groupInviteFragment)
        })

        mapViewModel.trackEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.mapFragment)
        })
    }
}
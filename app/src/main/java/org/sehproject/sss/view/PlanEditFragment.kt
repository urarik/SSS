package org.sehproject.sss.view

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
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentPlanDetailBinding
import org.sehproject.sss.databinding.FragmentPlanEditBinding
import org.sehproject.sss.viewmodel.MapViewModel
import org.sehproject.sss.viewmodel.PlanViewModel

class PlanEditFragment : Fragment() {
    val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }
    private val safeArgs: PlanEditFragmentArgs by navArgs() //plan
    //plan id가 null이면 create else edit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val planEditBinding: FragmentPlanEditBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_plan_edit, container, false)
        planEditBinding.planLogic = planViewModel.planLogic
        val view = planEditBinding.root

        initObserver()

        return view
    }

    fun initObserver() {
        val navController = findNavController()
        planViewModel.editCompletePlanEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_planEditFragment_to_planListFragment)
        })
    }
}
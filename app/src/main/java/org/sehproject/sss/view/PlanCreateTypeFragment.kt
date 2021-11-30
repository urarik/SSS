package org.sehproject.sss.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentPlanCreateTypeBinding
import org.sehproject.sss.viewmodel.PlanViewModel

class PlanCreateTypeFragment : Fragment() {
    val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val planCreateTypeBinding: FragmentPlanCreateTypeBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_plan_create_type,
            container,
            false
        )
        initObserver()

        planCreateTypeBinding.planLogic = planViewModel.planLogic
        planCreateTypeBinding.planType = ""

        return planCreateTypeBinding.root
    }

    private fun initObserver() {
        planViewModel.createPlanTypeFailEvent.observe(viewLifecycleOwner, {
            Toast.makeText(context, "약속 생성 실패!", Toast.LENGTH_SHORT).show()
        })
        planViewModel.createPlanCompleteEvent.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
    }

}
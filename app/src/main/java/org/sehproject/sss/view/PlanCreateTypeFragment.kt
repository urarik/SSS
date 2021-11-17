package org.sehproject.sss.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentPlanCreateOcrBinding
import org.sehproject.sss.viewmodel.PlanViewModel

class PlanCreateTypeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val planViewModel: PlanViewModel by lazy {
            ViewModelProvider(this).get(PlanViewModel::class.java)
        }

        val planCreateTypeBinding: FragmentPlanCreateOcrBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_plan_create_type, container, false)
        planCreateTypeBinding.planLogic = planViewModel.planLogic
        val view = planCreateTypeBinding.root

        return view
    }
}
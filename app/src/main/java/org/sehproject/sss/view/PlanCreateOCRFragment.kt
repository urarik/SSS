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
import org.sehproject.sss.viewmodel.PlanViewModel
import org.sehproject.sss.viewmodel.RecommendViewModel

class PlanCreateOCRFragment : Fragment() {
    val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val planCreateOCRBinding: FragmentPlanCreateOcrBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_plan_create_ocr, container, false)
        planCreateOCRBinding.planLogic = planViewModel.planLogic
        val view = planCreateOCRBinding.root

        return view
    }
}
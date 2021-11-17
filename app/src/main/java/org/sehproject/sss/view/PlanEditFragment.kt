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
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentPlanDetailBinding
import org.sehproject.sss.databinding.FragmentPlanEditBinding
import org.sehproject.sss.viewmodel.PlanViewModel

class PlanEditFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val planViewModel: PlanViewModel by lazy {
            ViewModelProvider(this).get(PlanViewModel::class.java)
        }

        val planEditBinding: FragmentPlanEditBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_plan_edit, container, false)
        planEditBinding.planLogic = planViewModel.planLogic
        val view = planEditBinding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.buttonSavePlan)
        button?.setOnClickListener {
            findNavController().navigate(R.id.action_planEditFragment_to_planListFragment, null)
        }
    }
}
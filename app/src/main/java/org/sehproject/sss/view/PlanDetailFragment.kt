package org.sehproject.sss.view

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
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
        val navController = findNavController()
        planViewModel.editPlanEvent.observe(viewLifecycleOwner, {
            val action = PlanDetailFragmentDirections.actionPlanDetailFragmentToPlanEditFragment(it)
            navController.navigate(action)
        })

        planViewModel.kickOutPlanEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.groupInviteFragment)
        })

        planViewModel.invitePlanEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.groupInviteFragment)
        })

        mapViewModel.trackEvent.observe(viewLifecycleOwner, {
            val action = PlanDetailFragmentDirections.actionPlanDetailFragmentToMapFragment(it)
            navController.navigate(action)
        })

        planViewModel.createMemoEvent.observe(viewLifecycleOwner, {
            val editText = EditText(context)

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("메모")
            builder.setView(editText)
            builder.setPositiveButton("입력", DialogInterface.OnClickListener { dialog, which ->
                //Toast.makeText(applicationContext, editText.text, Toast.LENGTH_SHORT).show()
            })
            builder.show()
        })
    }
}
package org.sehproject.sss.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentPlanEditBinding
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.viewmodel.GroupViewModel
import org.sehproject.sss.viewmodel.PlanViewModel
import java.text.SimpleDateFormat

class PlanEditFragment : Fragment() {
    val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }
    val groupViewModel: GroupViewModel by lazy {
        ViewModelProvider(this).get(GroupViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val planEditBinding: FragmentPlanEditBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_plan_edit, container, false)
        planEditBinding.planLogic = planViewModel.planLogic

        val safeArgs: PlanEditFragmentArgs by navArgs()
        //plan id가 null이면 create else edit
        planEditBinding.plan = safeArgs.plan
        groupViewModel.getGroupList()

        initObserver(planEditBinding, safeArgs.plan)

        return planEditBinding.root
    }

    fun initObserver(planEditBinding: FragmentPlanEditBinding, plan: Plan) {
        val navController = findNavController()
        planViewModel.editCompletePlanEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.action_planEditFragment_to_planListFragment)
        })
        planViewModel.startDatePickEvent.observe(viewLifecycleOwner, {
            pickDateTime(it, true, planEditBinding)
        })
        planViewModel.endDatePickEvent.observe(viewLifecycleOwner, {
            pickDateTime(it, false, planEditBinding)
        })
        groupViewModel.groupListLiveData.observe(viewLifecycleOwner, {
            planEditBinding.spinnerPlanGroup.adapter =
                ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, it)
            if(plan.group.gid != null) {
                for (i in 0 until it.count()) {
                    if (plan.group.name == it[i].name) {
                        planEditBinding.spinnerPlanGroup.setSelection(i)
                        break
                    }
                }
            }
        })

    }
    private fun pickDateTime(plan: Plan, isStart: Boolean, planEditBinding: FragmentPlanEditBinding) {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
            TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

                if(isStart) plan.startTime = dateFormat.format(pickedDateTime.time)
                else plan.endTime = dateFormat.format(pickedDateTime.time)
                planEditBinding.invalidateAll()

            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }
}
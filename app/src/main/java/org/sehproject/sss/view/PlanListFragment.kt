package org.sehproject.sss.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.UserInfo
import org.sehproject.sss.databinding.FragmentPlanListBinding
import org.sehproject.sss.databinding.ItemPlanBinding
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.viewmodel.PlanViewModel

class PlanListFragment : Fragment() {
    private val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }
    private var isOpen = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val planListBinding: FragmentPlanListBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_plan_list, container, false)
        planListBinding.planLogic = planViewModel.planLogic
        planListBinding.spinnerPlanOrder

        initObserver(planListBinding.RecyclerViewPlanList)
        planViewModel.setPlanList(true)

        val data = resources.getStringArray(R.array.planOrder)
        val spinnerAdapter = ArrayAdapter(activity as MainActivity, android.R.layout.simple_list_item_1, data)
        planListBinding.spinnerPlanOrder.adapter = spinnerAdapter

        planListBinding.spinnerPlanOrder.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0 && planViewModel.planListLiveData.value != null) {
                    planViewModel.planListLiveData.value =
                        planViewModel.planLogic.sortPlanByTime(planViewModel.planListLiveData.value!!)
                    val adapter = PlanAdapter(planViewModel.planListLiveData.value!!)
                    planListBinding.RecyclerViewPlanList.adapter = adapter
                    planViewModel.is_sorted = false
                } else if (p2 == 1&& planViewModel.planListLiveData.value != null) {
                    planViewModel.planListLiveData.value =
                        planViewModel.planLogic.sortPlanByCategory(planViewModel.planListLiveData.value!!)
                    val adapter = PlanAdapter(planViewModel.planListLiveData.value!!)
                    planListBinding.RecyclerViewPlanList.adapter = adapter
                    planViewModel.is_sorted = true
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        return planListBinding.root
    }

    private fun initObserver(recyclerView: RecyclerView) {
        val navController = findNavController()

        planViewModel.viewPlanDetailsEvent.observe(viewLifecycleOwner, {
            val action = PlanListFragmentDirections.actionPlanListFragmentToPlanDetailFragment(it)
            navController.navigate(action)
        })

        planViewModel.createPlanEvent.observe(viewLifecycleOwner, {
            val action = PlanListFragmentDirections.actionPlanListFragmentToPlanEditFragment(Plan())
            navController.navigate(action)
        })

        planViewModel.createPlanOcrEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.planCreateOCRFragment)
        })

        planViewModel.createPlanTypeEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.planCreateTypeFragment)
        })
        planViewModel.planListLiveData.observe(viewLifecycleOwner, {
            val adapter = PlanAdapter(it)
            recyclerView.adapter = adapter
        })
        planViewModel.isLastPlan.observe(viewLifecycleOwner, {
            planViewModel.setPlanList(it)
        })

    }

    private inner class PlanHolder(val itemPlanBinding: ItemPlanBinding) : RecyclerView.ViewHolder(itemPlanBinding.root) {
        fun bind(plan: Plan) {
            itemPlanBinding.plan = plan
            itemPlanBinding.planLogic = planViewModel.planLogic
            if(plan.creator == UserInfo.userId)
                itemPlanBinding.switchPublicPlan.visibility = ViewGroup.VISIBLE
        }
    }

    private inner class PlanAdapter(val plans: List<Plan>) :
        RecyclerView.Adapter<PlanHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanHolder {
            val itemPlanBinding = ItemPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PlanHolder(itemPlanBinding)
        }

        override fun getItemCount(): Int = plans.size

        override fun onBindViewHolder(holder: PlanHolder, position: Int) {
            val plan = plans[position]
            holder.bind(plan)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("TestTest", "Yeah~")
    }
}
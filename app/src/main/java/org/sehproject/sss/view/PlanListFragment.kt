package org.sehproject.sss.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentPlanListBinding
import org.sehproject.sss.databinding.ItemUserBinding
import org.sehproject.sss.databinding.ViewPlanItemBinding
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.PlanViewModel
import org.sehproject.sss.viewmodel.UserViewModel

class PlanListFragment : Fragment() {
    private lateinit var adapter: PlanAdapter
    private lateinit var planListBinding: FragmentPlanListBinding
    private val planViewModel: PlanViewModel by lazy {
        ViewModelProvider(this).get(PlanViewModel::class.java)
    }
    private var isOpen = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        planListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_plan_list, container, false)
        planListBinding.planLogic = planViewModel.planLogic
        initObserver()

        return planListBinding.root
    }

    private fun initObserver() {
        val navController = findNavController()

        planViewModel.viewPlanDetailsEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.planDetailFragment)
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
    }

    private inner class PlanHolder(val itemPlanBinding: ViewPlanItemBinding) : RecyclerView.ViewHolder(itemPlanBinding.root) {

        fun bind(plan: Plan) {
            itemPlanBinding.plan = plan
            /*
            itemPlanBinding.itemPlan.setOnClickListener {
                planViewModel?.let {
                    planViewModel!!.planLogic.onItemClick(itemPlanBinding.plan as Plan)
                }
            }
             */
        }
    }

    private inner class PlanAdapter(val plans: List<Plan>) :
        RecyclerView.Adapter<PlanHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanHolder {
            val itemCuisinesBinding = ViewPlanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PlanHolder(itemCuisinesBinding)
        }

        override fun getItemCount(): Int = plans.size

        override fun onBindViewHolder(holder: PlanHolder, position: Int) {
            val plan = plans[position]
            holder.bind(plan)
        }

    }
}
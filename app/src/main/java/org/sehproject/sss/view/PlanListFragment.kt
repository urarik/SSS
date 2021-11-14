package org.sehproject.sss.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentPlanListBinding
import org.sehproject.sss.viewmodel.PlanViewModel
import org.sehproject.sss.viewmodel.UserViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [PlanListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlanListFragment : Fragment() {
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

        val floatingButton = planListBinding.floatingActionButton3
        val buttonList = mutableListOf<FloatingActionButton>()
        buttonList.add(planListBinding.MakePlanFloatingActionButton)
        buttonList.add(planListBinding.MakePlanOCRFloatingActionButton)
        buttonList.add(planListBinding.MakePlanTypeFloatingActionButton)
        floatingButton.setOnClickListener { view ->
            if (isOpen) {
                for (button in buttonList) {
                    val anim = AnimationUtils.loadAnimation(context, R.anim.fab_close)
                    button.startAnimation(anim)
                    button.isClickable = false
                }
                isOpen = false;
            } else {
                val anim = AnimationUtils.loadAnimation(context, R.anim.fab_open)
                for (button in buttonList) {
                    button.startAnimation(anim)
                    button.isClickable = true
                }
                isOpen = true;
            }
        }

        return planListBinding.root
    }

    private fun initObserver() {

    }
}
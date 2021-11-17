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
import androidx.navigation.fragment.findNavController
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

        return planListBinding.root
    }

    private fun initObserver() {

    }
}
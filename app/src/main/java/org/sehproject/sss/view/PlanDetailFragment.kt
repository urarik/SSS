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
import org.sehproject.sss.databinding.FragmentPlanDetailBinding
import org.sehproject.sss.viewmodel.PlanViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlanDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlanDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val planViewModel: PlanViewModel by lazy {
            ViewModelProvider(this).get(PlanViewModel::class.java)
        }

        val planDetailBinding: FragmentPlanDetailBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_plan_detail, container, false)
        planDetailBinding.planLogic = planViewModel.planLogic
        val view = planDetailBinding.root

        return view
    }
}
package org.sehproject.sss.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentRecommendBinding
import org.sehproject.sss.databinding.FragmentUserSearchBinding
import org.sehproject.sss.viewmodel.FriendViewModel
import org.sehproject.sss.viewmodel.RecommendViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecommendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecommendFragment : Fragment() {
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
        val recommendViewModel: RecommendViewModel by lazy {
            ViewModelProvider(this).get(RecommendViewModel::class.java)
        }

        val recommendBinding: FragmentRecommendBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_recommend, container, false)
        recommendBinding.recommendLogic = recommendViewModel.recommendLogic
        val view = recommendBinding.root

        return view
    }
}
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

class RecommendFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recommendViewModel: RecommendViewModel by lazy {
            ViewModelProvider(this).get(RecommendViewModel::class.java)
        }

        val recommendBinding: FragmentRecommendBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_recommend, container, false)
        recommendBinding.recommendLogic = recommendViewModel.recommendLogic

        return recommendBinding.root
    }
}
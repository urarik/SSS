package org.sehproject.sss.logic

import androidx.lifecycle.MutableLiveData
import org.sehproject.sss.viewmodel.RecommendViewModel

class RecommendLogic(val recommendViewModel:RecommendViewModel) {
    val recommendRepository = recommendViewModel.recommendRepository
    fun onRecommendClick() {}

}
package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.datatype.Place
import org.sehproject.sss.logic.RecommendLogic
import org.sehproject.sss.repository.RecommendRepository
import org.sehproject.sss.utils.SingleLiveEvent

class RecommendViewModel : ViewModel() {
    val recommendLogic = RecommendLogic(this)
    val recommendRepository = RecommendRepository()

    fun getPlaces() {
        recommendRepository.getPopularityList { code, list ->
            if(code == 0)
                popularPlaceLiveData.value = list
        }
    }

    val popularPlaceLiveData = MutableLiveData<List<String>>()
    val recommendEvent = SingleLiveEvent<Any>()
    val recommendResultEvent = SingleLiveEvent<Any>()
}
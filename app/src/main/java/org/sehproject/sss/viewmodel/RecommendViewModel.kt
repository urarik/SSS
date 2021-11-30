package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.repository.RecommendRepository

class RecommendViewModel : ViewModel() {
    val recommendRepository = RecommendRepository()

    val popularPlaceLiveData = MutableLiveData<List<String>>()

    fun setPlaces() {
        recommendRepository.getPopularityList { code, list ->
            if(code == 0)
                popularPlaceLiveData.value = list
        }
    }
}
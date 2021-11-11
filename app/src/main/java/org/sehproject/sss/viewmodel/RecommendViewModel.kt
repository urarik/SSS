package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.logic.RecommendLogic
import org.sehproject.sss.utils.SingleLiveEvent

class RecommendViewModel : ViewModel() {
    val recommendLogic = RecommendLogic(this)

    val popularPlaceLiveData = MutableLiveData<List<String>>()
    val recommendEvent = SingleLiveEvent<Any>()
    val recommendResultEvent = SingleLiveEvent<Any>()
}
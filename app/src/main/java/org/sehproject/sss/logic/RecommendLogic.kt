package org.sehproject.sss.logic

import androidx.lifecycle.MutableLiveData
import org.sehproject.sss.viewmodel.RecommendViewModel

class RecommendLogic(val recommendViewModel:RecommendViewModel) {
    fun onRecommendClick() {}

    private fun convertList(listToLd:List<String>): MutableLiveData<List<String>>
    {
        var liveData = MutableLiveData<List<String>>()
        return liveData
    }
}
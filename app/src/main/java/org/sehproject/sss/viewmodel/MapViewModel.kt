package org.sehproject.sss.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.datatype.Location
import org.sehproject.sss.logic.MapLogic
import org.sehproject.sss.repository.MapRepository
import org.sehproject.sss.utils.SingleLiveEvent

class MapViewModel : ViewModel() {
    val mapLogic = MapLogic(this)
    val mapRepository = MapRepository()

    val locationListLiveData = MutableLiveData<List<Location>>()
    val trackEvent = SingleLiveEvent<Int>()

    fun setLocationList(pid: Int) {
        mapRepository.getLocationList(pid) {code, list ->
            var mlist = mutableListOf<Location>()
            if(code == 0)
                mlist = if (list == null)
                    mutableListOf()
                else
                    list as MutableList<Location>
                locationListLiveData.value = mlist
            Log.d("TAG", list.toString())
        }
    }
}
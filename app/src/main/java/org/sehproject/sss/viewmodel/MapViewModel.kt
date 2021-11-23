package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.datatype.Eta
import org.sehproject.sss.logic.MapLogic
import org.sehproject.sss.repository.MapRepository
import org.sehproject.sss.utils.SingleLiveEvent
import java.time.LocalTime

class MapViewModel : ViewModel() {
    val mapLogic = MapLogic(this)
    val mapRepository = MapRepository()
    // val locationLiveData = MutableLiveData<List<Coordinate>>()
    val etaListLiveData = MutableLiveData<List<Eta>>()
    val trackEvent = SingleLiveEvent<Int>()
    val locationUpdateEvent = SingleLiveEvent<Any>()
    val etaUpdateEvent = SingleLiveEvent<Any>()
}
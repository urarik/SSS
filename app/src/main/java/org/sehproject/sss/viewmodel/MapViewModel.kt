package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.logic.MapLogic
import org.sehproject.sss.utils.SingleLiveEvent
import java.time.LocalTime

class MapViewModel : ViewModel() {
    val mapLogic = MapLogic(this)

    // val locationLiveData = MutableLiveData<List<Coordinate>>()
    val etaLiveData = MutableLiveData<List<LocalTime>>()
    val trackEvent = SingleLiveEvent<Any>()
    val locationUpdateEvent = SingleLiveEvent<Any>()
    val etaUpdateEvent = SingleLiveEvent<Any>()
}
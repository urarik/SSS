package org.sehproject.sss.logic

import org.sehproject.sss.viewmodel.MapViewModel

class MapLogic(val mapViewModel: MapViewModel) {
    fun onShareMyLocationClick()
    {
        mapViewModel.trackEvent.call()
    }

    fun onCancelClick()
    {

    }

    fun onMapViewInitialized()
    {

    }

    fun onCurrentLocationUpdate()
    {

    }

    fun onCurrentLocationUpdateCancelled()
    {

    }
}
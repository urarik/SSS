package org.sehproject.sss.logic

import org.sehproject.sss.datatype.Coordinate
import org.sehproject.sss.datatype.Plan
import org.sehproject.sss.viewmodel.MapViewModel

class MapLogic(private val mapViewModel: MapViewModel) {
    fun onShareMyLocationClick(pid: Int)
    {
        mapViewModel.trackEvent.value = pid
    }

    fun onCancelClick()
    {

    }

    fun onMapViewInitialized()
    {

    }

    fun onCurrentLocationUpdate(coordinate: Coordinate)
    {
        mapViewModel.mapRepository.setLocation(coordinate) {code ->
            if(code == 0) {

            }
        }
    }

    fun getLocationList(pid: Int) {
        mapViewModel.mapRepository.getLocationList(pid) {code, list ->
            if(code == 0)
                mapViewModel.locationListLiveData.value = list
        }
    }

    fun onCurrentLocationUpdateCancelled()
    {

    }
}
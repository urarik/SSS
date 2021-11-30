package org.sehproject.sss.logic

import org.sehproject.sss.datatype.Coordinate
import org.sehproject.sss.viewmodel.MapViewModel

class MapLogic(private val mapViewModel: MapViewModel) {
    fun onShareMyLocationClick(pid: Int)
    {
        mapViewModel.trackEvent.value = pid
    }

    fun onCurrentLocationUpdate(coordinate: Coordinate)
    {
        mapViewModel.mapRepository.setLocation(coordinate) {_ ->
        }
    }
}
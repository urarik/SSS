package org.sehproject.sss.repository

import org.sehproject.sss.ServerApi
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.Coordinate
import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.datatype.Location
import org.sehproject.sss.datatype.LocationListResponse
import org.sehproject.sss.service.MapService
import org.sehproject.sss.utils.CallbackWithRetry
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapRepository {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var mapService: MapService = retrofit.create(MapService::class.java)

    fun setLocation(coordinate: Coordinate, onResult: (Int) -> Unit) {
        val setLocationCall = mapService.requestSetLocation(
            UserInfo.userId,
            coordinate.latitude,
            coordinate.longitude
        )
        setLocationCall.enqueue(object : CallbackWithRetry<GenericResponse>(setLocationCall) {
            override fun onResponse(
                call: Call<GenericResponse>,
                response: Response<GenericResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    onResult(0)
                } else {
                    onResult(1)
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1)
                }
            }
        })
    }

    fun getLocationList(pid: Int, onResult: (Int, List<Location>?) -> Unit) {
        val getLocationListCall = mapService.requestGetLocationList(pid)
        getLocationListCall.enqueue(object : CallbackWithRetry<LocationListResponse>(getLocationListCall) {
            override fun onResponse(
                call: Call<LocationListResponse>,
                response: Response<LocationListResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    val location = response.body()?.location
                    onResult(0, location)
                } else {
                    onResult(1, mutableListOf())
                }
            }

            override fun onFailure(call: Call<LocationListResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1, null)
                }
            }
        })
    }
}
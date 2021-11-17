package org.sehproject.sss.repository

import org.sehproject.sss.ServerApi
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.Coordinate
import org.sehproject.sss.datatype.CoordinateResponse
import org.sehproject.sss.datatype.EtaResponse
import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.service.MapService
import org.sehproject.sss.service.PlanService
import org.sehproject.sss.utils.CallbackWithRetry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalTime

class MapRepository {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var mapService: MapService = retrofit.create(MapService::class.java)

    fun setLocation(coordinate: Coordinate, onResult: (Int) -> Unit) {
        mapService.requestSetLocation(UserInfo.userId, coordinate.latitude, coordinate.longitude)
            .enqueue(object : CallbackWithRetry<GenericResponse>(
                mapService.requestSetLocation(
                    UserInfo.userId,
                    coordinate.latitude,
                    coordinate.longitude
                )
            ) {
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

    fun getLocation(userId: String, onResult: (Int, Coordinate?) -> Unit) {
        mapService.requestGetLocation(userId)
            .enqueue(object :
                CallbackWithRetry<CoordinateResponse>(mapService.requestGetLocation(userId)) {
                override fun onResponse(
                    call: Call<CoordinateResponse>,
                    response: Response<CoordinateResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val coordinate = response.body()?.coordinate
                        onResult(0, coordinate)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<CoordinateResponse>, t: Throwable) {
                    super.onFailure {
                        onResult(-1, null)
                    }
                }
            })
    }

    fun getEta(
        startAddress: String,
        destinationAddress: String,
        onResult: (Int, LocalTime?) -> Unit
    ) {
        mapService.requestGetEta(startAddress, destinationAddress)
            .enqueue(object : CallbackWithRetry<EtaResponse>(
                mapService.requestGetEta(
                    startAddress,
                    destinationAddress
                )
            ) {
                override fun onResponse(
                    call: Call<EtaResponse>,
                    response: Response<EtaResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val eta = response.body()?.eta
                        onResult(0, eta)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<EtaResponse>, t: Throwable) {
                    super.onFailure {
                        onResult(-1, null)
                    }
                }
            })
    }
}
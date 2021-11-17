package org.sehproject.sss.repository

import org.sehproject.sss.ServerApi
import org.sehproject.sss.service.MapService
import org.sehproject.sss.service.PlanService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapRepository {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var mapService: MapService = retrofit.create(MapService::class.java)
}
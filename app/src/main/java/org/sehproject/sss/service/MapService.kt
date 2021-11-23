package org.sehproject.sss.service

import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.datatype.LocationListResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MapService {

    @POST("map/location/set")
    @FormUrlEncoded
    fun requestSetLocation(
        @Field("userid")
        userId: String,

        @Field("latitude")
        latitude: Double,

        @Field("longitude")
        longitude: Double
    ): Call<GenericResponse>

    @POST("map/location")
    @FormUrlEncoded
    fun requestGetLocationList(
        @Field("pid")
        pid: Int
    ): Call<LocationListResponse>
}
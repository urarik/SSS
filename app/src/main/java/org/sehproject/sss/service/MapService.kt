package org.sehproject.sss.service

import org.sehproject.sss.datatype.CoordinateResponse
import org.sehproject.sss.datatype.EtaResponse
import org.sehproject.sss.datatype.GenericResponse
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
    fun requestGetLocation(
        @Field("userid")
        userId: String
    ): Call<CoordinateResponse>

    @POST("map/eta")
    @FormUrlEncoded
    fun requestGetEta(
        @Field("start_address")
        startAddress: String,

        @Field("destination_address")
        destinationAddress: String
    ): Call<EtaResponse>
}
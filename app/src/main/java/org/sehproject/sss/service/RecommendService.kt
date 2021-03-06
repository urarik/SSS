package org.sehproject.sss.service

import org.sehproject.sss.datatype.PlaceListResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RecommendService {

    @POST("/map/popularity")
    @FormUrlEncoded
    fun requestPopularityList(
        @Field("userid")
        userId: String
    ): Call<PlaceListResponse>

}
package org.sehproject.sss.repository

import org.sehproject.sss.ServerApi
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.*
import org.sehproject.sss.service.RecommendService
import org.sehproject.sss.utils.CallbackWithRetry
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecommendRepository {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var recommendService: RecommendService = retrofit.create(RecommendService::class.java)

    //TODO("Popularity를 Place로 교체")
    fun getPopularityList(onResult: (Int, List<String>?) -> Unit) {
        val getPopularityListCall = recommendService.requestPopularityList(UserInfo.userId)
        getPopularityListCall.enqueue(object :
            CallbackWithRetry<PlaceListResponse>(getPopularityListCall) {
            override fun onResponse(
                call: Call<PlaceListResponse>,
                listResponse: Response<PlaceListResponse>
            ) {
                val code = listResponse.body()?.code
                if (code == 0) {
                    val placeList = listResponse.body()?.name
                    onResult(0, placeList)
                } else {
                    onResult(1, null)
                }
            }

            override fun onFailure(call: Call<PlaceListResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1, null)
                }
            }
        })
    }

    fun getKeyWord(target: Coordinate, onResult: (Int, String?) -> Unit) {
        val getKeyWordCall = recommendService.requestKeyword(target.latitude, target.longitude)
        getKeyWordCall.enqueue(object : CallbackWithRetry<KeywordResponse>(getKeyWordCall) {
            override fun onResponse(
                call: Call<KeywordResponse>,
                listResponse: Response<KeywordResponse>
            ) {
                val code = listResponse.body()?.code
                if (code == 0) {
                    val keyword = listResponse.body()?.keyword
                    onResult(0, keyword)
                } else {
                    onResult(1, null)
                }
            }

            override fun onFailure(call: Call<KeywordResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1, null)
                }
            }
        })
    }
}
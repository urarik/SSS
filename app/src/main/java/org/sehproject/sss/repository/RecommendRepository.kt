package org.sehproject.sss.repository

import org.sehproject.sss.ServerApi
import org.sehproject.sss.datatype.Coordinate
import org.sehproject.sss.datatype.KeywordResponse
import org.sehproject.sss.datatype.Popularity
import org.sehproject.sss.datatype.PopularityListResponse
import org.sehproject.sss.service.RecommendService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecommendRepository {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var recommendService: RecommendService = retrofit.create(RecommendService::class.java)

    fun getPopularityList(userId: String, onResult: (Int, List<Popularity>?) -> Unit) {
        recommendService.requestPopularityList(userId)
            .enqueue(object : Callback<PopularityListResponse> {
                override fun onResponse(
                    call: Call<PopularityListResponse>,
                    listResponse: Response<PopularityListResponse>
                ) {
                    val code = listResponse.body()?.code
                    if (code == 0) {
                        val popularityList = listResponse.body()?.popularityList
                        onResult(0, popularityList)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<PopularityListResponse>, t: Throwable) {
                    onResult(-1, null)
                }
            })
    }

    fun getKeyWord(target: Coordinate, onResult: (Int, String?) -> Unit) {
        recommendService.requestKeyword(target.latitude, target.longitude)
            .enqueue(object : Callback<KeywordResponse> {
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
                    onResult(-1, null)
                }
            })
    }
}
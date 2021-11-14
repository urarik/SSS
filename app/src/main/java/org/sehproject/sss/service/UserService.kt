package org.sehproject.sss.service

import org.sehproject.sss.datatype.GenericResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {

    @POST("login")
    @FormUrlEncoded
    fun loginRequest(
        @Field("userid")
        userId: String,

        @Field("password")
        password: String,
    ): Call<GenericResponse>

    @POST("register")
    @FormUrlEncoded
    fun registerRequest(
        @Field("userid")
        userId: String,

        @Field("password")
        password: String,

        @Field("nickname")
        nickName: String
    ): Call<GenericResponse>
}
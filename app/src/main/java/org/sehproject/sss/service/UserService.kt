package org.sehproject.sss.service

import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.datatype.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {

    @POST("user/login")
    @FormUrlEncoded
    fun requestLogin(
        @Field("userid")
        userId: String,

        @Field("password")
        password: String,
    ): Call<GenericResponse>

    @POST("user/register")
    @FormUrlEncoded
    fun requestRegister(
        @Field("userid")
        userId: String,

        @Field("password")
        password: String,

        @Field("nickname")
        nickName: String
    ): Call<GenericResponse>

    @POST("user/user")
    @FormUrlEncoded
    fun requestGetUser(
        @Field("userid")
        userId: String
    ): Call<UserResponse>
}
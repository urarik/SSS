package org.sehproject.sss.service

import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.datatype.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {

    @POST("auth/login")
    @FormUrlEncoded
    fun requestLogin(
        @Field("userid")
        userId: String,

        @Field("password")
        password: String,
        
        @Field("token")
        token: String
    ): Call<UserResponse>

    @POST("auth/login/api")
    @FormUrlEncoded
    fun requestApiLogin(
        @Field("api_id")
        apiId: String,

        @Field("token")
        token: String
    ) : Call<UserResponse>

    @POST("auth/register/api")
    @FormUrlEncoded
    fun requestApiRegister(
        @Field("userid")
        userId: String,

        @Field("nickname")
        nickName: String,

        @Field("api_id")
        apiId: String
    ) : Call<GenericResponse>

    @POST("auth/register")
    @FormUrlEncoded
    fun requestRegister(
        @Field("userid")
        userId: String,

        @Field("password")
        password: String,

        @Field("nickname")
        nickName: String
    ): Call<GenericResponse>

    @POST("auth/user")
    @FormUrlEncoded
    fun requestGetUser(
        @Field("userid")
        userId: String
    ): Call<UserResponse>
}
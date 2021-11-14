package org.sehproject.sss.service

import org.sehproject.sss.datatype.FriendListResponse
import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.datatype.UserListResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FriendService {

    @POST("searchuser")
    @FormUrlEncoded
    fun searchUserRequest(
        @Field("useridornickname")
        userId: String
    ): Call<UserListResponse>

    @POST("addfriend")
    @FormUrlEncoded
    fun addFriendRequest(
        @Field("userid")
        userId: String,

        @Field("frienduserid")
        friendUserId: String
    ): Call<GenericResponse>

    @POST("deletefriend")
    @FormUrlEncoded
    fun deleteFriendRequest(
        @Field("userid")
        userId: String,

        @Field("frienduserid")
        friendUserId: String
    ): Call<GenericResponse>

    @POST("blockfriend")
    @FormUrlEncoded
    fun blockFriendRequest(
        @Field("userid")
        userId: String,

        @Field("frienduserid")
        friendUserId: String
    ): Call<GenericResponse>

    @POST("getfriendlist")
    @FormUrlEncoded
    fun getFriendListRequest(
        @Field("userid")
        userId: String
    ) : Call<FriendListResponse>
}
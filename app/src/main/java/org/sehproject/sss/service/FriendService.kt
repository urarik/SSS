package org.sehproject.sss.service

import org.sehproject.sss.datatype.FriendListResponse
import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.datatype.UserListResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FriendService {

    @POST("friend/search")
    @FormUrlEncoded
    fun searchUserRequest(
        @Field("userid_or_nickname")
        userId: String
    ): Call<UserListResponse>

    @POST("friend/add")
    @FormUrlEncoded
    fun addFriendRequest(
        @Field("userid")
        userId: String,

        @Field("friend_userid")
        friendUserId: String
    ): Call<GenericResponse>

    @POST("friend/delete")
    @FormUrlEncoded
    fun deleteFriendRequest(
        @Field("userid")
        userId: String,

        @Field("friend_userid")
        friendUserId: String
    ): Call<GenericResponse>

    @POST("friend/block")
    @FormUrlEncoded
    fun blockFriendRequest(
        @Field("userid")
        userId: String,

        @Field("friend_userid")
        friendUserId: String
    ): Call<GenericResponse>

    @POST("friend/list")
    @FormUrlEncoded
    fun getFriendListRequest(
        @Field("userid")
        userId: String
    ) : Call<FriendListResponse>
}
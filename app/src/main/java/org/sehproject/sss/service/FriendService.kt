package org.sehproject.sss.service

import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.datatype.UserListResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FriendService {

    @POST("friend/search")
    @FormUrlEncoded
    fun requestSearchUser(
        @Field("userid")
        userId: String,

        @Field("userid_or_nickname")
        userIdOrNickName: String
    ): Call<UserListResponse>

    @POST("friend/add")
    @FormUrlEncoded
    fun requestAddFriend(
        @Field("userid")
        userId: String,

        @Field("target_userid")
        friendUserId: String
    ): Call<GenericResponse>

    @POST("friend/delete")
    @FormUrlEncoded
    fun requestDeleteFriend(
        @Field("userid")
        userId: String,

        @Field("target_userid")
        friendUserId: String
    ): Call<GenericResponse>

    @POST("friend/block")
    @FormUrlEncoded
    fun requestBlockFriend(
        @Field("userid")
        userId: String,

        @Field("target_userid")
        friendUserId: String
    ): Call<GenericResponse>

    @POST("friend/list")
    @FormUrlEncoded
    fun requestGetFriendList(
        @Field("userid")
        userId: String
    ) : Call<UserListResponse>
}
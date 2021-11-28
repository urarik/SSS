package org.sehproject.sss.service

import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.datatype.GroupListResponse
import org.sehproject.sss.datatype.GroupResponse
import org.sehproject.sss.datatype.UserListResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GroupService {

    @POST("group/create")
    @FormUrlEncoded
    fun requestCreateGroup(
        @Field("name")
        name: String,

        @Field("explanation")
        explanation: String,

        @Field("color")
        color: Int,

        @Field("creator")
        creator: String
    ): Call<GenericResponse>

    @POST("group/delete")
    @FormUrlEncoded
    fun requestDeleteGroup(
        @Field("gid")
        groupId: Int
    ): Call<GenericResponse>

    @POST("group/exit")
    @FormUrlEncoded
    fun requestExitGroup(
        @Field("gid")
        groupId: Int,

        @Field("userid")
        userId: String
    ): Call<GenericResponse>

    @POST("group/edit")
    @FormUrlEncoded
    fun requestEditGroup(
        @Field("gid")
        groupId: Int,

        @Field("name")
        name: String,

        @Field("explanation")
        explanation: String,

        @Field("color")
        color: Int
    ): Call<GenericResponse>

    @POST("group/invite")
    @FormUrlEncoded
    fun requestInviteGroup(
        @Field("gid")
        groupId: Int,
        @Field("userid")
        userId: String,
        @Field("target_userid_list")
        userIdList: List<String>
    ): Call<GenericResponse>

    @POST("group/accept")
    @FormUrlEncoded
    fun requestAcceptGroup(
        @Field("gid")
        groupId: Int,

        @Field("userid")
        userId: String,

        @Field("accept")
        isAccept: Boolean

    ): Call<GenericResponse>

    @POST("group/kick")
    @FormUrlEncoded
    fun requestKickOutGroup(
        @Field("gid")
        groupId: Int,

        @Field("target_userid_list")
        userIdList: List<String>
    ): Call<GenericResponse>

    @POST("group/list")
    @FormUrlEncoded
    fun requestGetGroupList(
        @Field("userid")
        userId: String
    ): Call<GroupListResponse>

    @POST("group/details")
    @FormUrlEncoded
    fun requestGetGroup(
        @Field("gid")
        groupId: Int
    ): Call<GroupResponse>

    @POST("group/participants")
    @FormUrlEncoded
    fun requestGetParticipantList(
        @Field("gid")
        planId: Int
    ): Call<UserListResponse>
}
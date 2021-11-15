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
        color: String,

        @Field("creator")
        creator: String
    ): Call<GenericResponse>

    @POST("group/delete")
    @FormUrlEncoded
    fun requestDeleteGroup(
        @Field("groupid")
        groupId: Int
    ): Call<GenericResponse>

    @POST("group/edit")
    @FormUrlEncoded
    fun requestEditGroup(
        @Field("groupid")
        groupId: Int,

        @Field("name")
        name: String,

        @Field("explanation")
        explanation: String,

        @Field("color")
        color: String
    ): Call<GenericResponse>

    @POST("group/invite")
    @FormUrlEncoded
    fun requestInviteGroup(
        @Field("groupid")
        groupId: Int,

        @Field("userid_list")
        userIdList: List<String>
    ): Call<GenericResponse>

    @POST("group/kickout")
    @FormUrlEncoded
    fun requestKickOutGroup(
        @Field("groupid")
        groupId: Int,

        @Field("userid_list")
        userIdList: List<String>
    ): Call<GenericResponse>

    @POST("group/group")
    @FormUrlEncoded
    fun requestGetGroupList(
        @Field("userid")
        userId: String
    ): Call<GroupListResponse>

    @POST("group/list")
    @FormUrlEncoded
    fun requestGetGroup(
        @Field("groupid")
        groupId: Int
    ): Call<GroupResponse>

    @POST("group/participants")
    @FormUrlEncoded
    fun requestGetParticipantList(
        @Field("groupid")
        planId: Int
    ): Call<UserListResponse>
}
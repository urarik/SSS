package org.sehproject.sss.service

import org.sehproject.sss.datatype.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PlanService {

    @POST("plan/create")
    @FormUrlEncoded
    fun requestCreatePlan(
        @Field("name")
        name: String,

        @Field("date")
        date: String,

        @Field("start_time")
        startTime: String,

        @Field("end_time")
        endTime: String,

        @Field("location")
        location: String,

        @Field("category")
        category: String,

        @Field("gid")
        gid: Int?,

        @Field("creator")
        creator: String
    ): Call<GenericResponse>

    @POST("plan/delete")
    @FormUrlEncoded
    fun requestDeletePlan(
        @Field("pid")
        planId: Int
    ): Call<GenericResponse>

    @POST("plan/edit")
    @FormUrlEncoded
    fun requestEditPlan(
        @Field("pid")
        pid: Int,

        @Field("name")
        name: String,

        @Field("start_time")
        startTime: String,

        @Field("end_time")
        endTime: String,

        @Field("location")
        location: String,

        @Field("category")
        category: String,

        @Field("gid")
        gid: Int?
    ): Call<GenericResponse>

    @POST("plan/complete")
    @FormUrlEncoded
    fun requestCompletePlan(
        @Field("pid")
        planId: Int,
    ): Call<GenericResponse>

    @POST("plan/cancel")
    @FormUrlEncoded
    fun requestCancelPlan(
        @Field("pid")
        planId: Int,

        @Field("userid")
        userId: String
    ): Call<GenericResponse>

    @POST("plan/invite")
    @FormUrlEncoded
    fun requestInvitePlan(
        @Field("pid")
        planId: Int,

        @Field("userid")
        userId: String,

        @Field("target_userid_list")
        userIdList: List<String>
    ): Call<GenericResponse>

    @POST("plan/accept")
    @FormUrlEncoded
    fun requestAcceptPlan(
        @Field("pid")
        planId: Int,

        @Field("userid")
        userId: String,

        @Field("accept")
        isAccept: Boolean

    ): Call<GenericResponse>

    @POST("plan/kick")
    @FormUrlEncoded
    fun requestKickOutPlan(
        @Field("pid")
        planId: Int,

        @Field("target_userid_list")
        userIdList: List<String>
    ): Call<GenericResponse>

    @POST("memo/create")
    @FormUrlEncoded
    fun requestCreateMemo(
        @Field("pid")
        planId: Int,

        @Field("userid")
        userId: String,

        @Field("memo")
        memo: String
    ): Call<GenericResponse>

    @POST("memo/delete")
    @FormUrlEncoded
    fun requestDeleteMemo(
        @Field("pid")
        planId: Int,

        @Field("userid")
        userId: String
    ): Call<GenericResponse>

    @POST("plan/public")
    @FormUrlEncoded
    fun requestSetPlanVisibility(
        @Field("pid")
        planId: Int,

        @Field("visibility")
        visibility: Boolean
    ): Call<GenericResponse>

    @POST("plan/list")
    @FormUrlEncoded
    fun requestGetPlanList(
        @Field("userid")
        userId: String,

        @Field("is_current")
        isCurrent: Boolean
    ): Call<PlanListResponse>

    @POST("plan/details")
    @FormUrlEncoded
    fun requestGetPlan(
        @Field("pid")
        planId: Int
    ): Call<PlanResponse>

    @POST("plan/partlist")
    @FormUrlEncoded
    fun requestGetParticipantList(
        @Field("pid")
        planId: Int
    ): Call<UserListResponse>

    @POST("memo/list")
    @FormUrlEncoded
    fun requestGetMemoList(
        @Field("pid")
        planId: Int
    ): Call<MemoListResponse>

    @POST("plan/point")
    @FormUrlEncoded
    fun requestAddPoint(
        @Field("userid")
        userId: String,

        @Field("point")
        point: Int
    ): Call<GenericResponse>
}
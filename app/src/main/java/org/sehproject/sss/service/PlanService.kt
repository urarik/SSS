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
        category: String
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

        @Field("target_userid_list")
        userIdList: List<String>
    ): Call<GenericResponse>

    @POST("plan/kickout")
    @FormUrlEncoded
    fun requestKickOutPlan(
        @Field("pid")
        planId: Int,

        @Field("target_userid_list")
        userIdList: List<String>
    ): Call<GenericResponse>

    @POST("plan/memo/create")
    @FormUrlEncoded
    fun requestCreateMemo(
        @Field("pid")
        planId: Int,

        @Field("userid")
        userId: String,

        @Field("memo")
        memo: String
    ): Call<GenericResponse>

    @POST("plan/memo/delete")
    @FormUrlEncoded
    fun requestDeleteMemo(
        @Field("pid")
        planId: Int,

        @Field("userid")
        userId: String
    ): Call<GenericResponse>

    @POST("plan/visibility")
    @FormUrlEncoded
    fun requestSetPlanVisibility(
        @Field("pid")
        planId: Int,

        @Field("visibility")
        visibility: Boolean
    ): Call<GenericResponse>

    @POST("plan/ocr")
    @FormUrlEncoded
    fun requestGetImageOcr(
        @Field("image")
        binaryImage: Byte
    ): Call<OcrResponse>

    @POST("plan/list")
    @FormUrlEncoded
    fun requestGetPlanList(
        @Field("userid")
        userId: String,

        @Field("is_current")
        isCurrent: Boolean
    ): Call<PlanListResponse>

    @POST("plan/plan")
    @FormUrlEncoded
    fun requestGetPlan(
        @Field("pid")
        planId: Int
    ): Call<PlanResponse>

    @POST("plan/participants")
    @FormUrlEncoded
    fun requestGetParticipantList(
        @Field("pid")
        planId: Int
    ): Call<UserListResponse>

    @POST("plan/memo/list")
    @FormUrlEncoded
    fun requestGetMemoList(
        @Field("pid")
        planId: Int
    ): Call<MemoListResponse>
}
package org.sehproject.sss.service

import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.datatype.ProfileResponse
import org.sehproject.sss.datatype.StatisticsResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ProfileService {
    @POST("profile/edit")
    @FormUrlEncoded
    fun requestEditProfile(
        @Field("userid")
        userId: String,

        @Field("nickname")
        nickName: String,

        @Field("name")
        name: String,

        @Field("age")
        age: Int,

        @Field("gender")
        gender: Boolean,

        // 이미지는 나중에 Multipart를 사용하도록 변경 필요
        @Field("image")
        image: String
    ): Call<GenericResponse>

    @POST("profile")
    @FormUrlEncoded
    fun requestGetProfile(
        @Field("userid")
        userId: String
    ): Call<ProfileResponse>

    // PlanService 로 통합 생각중
    /*
    @POST("profile/plan_list")
    @FormUrlEncoded
    fun requestGetPlanList(
        @Field("userid")
        userId: String
    ): Call<PlanListResponse>*/

    @POST("profile/statistics")
    @FormUrlEncoded
    fun requestGetStatistics(
        @Field("userid")
        userId: String
    ): Call<StatisticsResponse>

    @POST("profile/option")
    @FormUrlEncoded
    fun requestUpdateNoticeOption(
        @Field("userid")
        userId: String,

        @Field("notice_option")
        noticeOption: Boolean,

        @Field("friend_invite_option")
        friendInviteOption: Boolean,

        @Field("plan_invite_option")
        planInviteOption: Boolean
    ): Call<GenericResponse>

    @POST("logout")
    @FormUrlEncoded
    fun requestLogout(
        @Field("userid")
        userId: String
    ): Call<GenericResponse>
}
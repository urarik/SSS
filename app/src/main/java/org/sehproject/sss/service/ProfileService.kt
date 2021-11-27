package org.sehproject.sss.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.datatype.OptionResponse
import org.sehproject.sss.datatype.ProfileResponse
import org.sehproject.sss.datatype.StatisticsResponse
import retrofit2.Call
import retrofit2.http.*

interface ProfileService {
    @POST("profile/edit")
    @FormUrlEncoded
    fun requestEditProfile(
        @Field("userid")
        userId: String,

        @Field("nickname")
        nickName: String,

        @Field("username")
        username: String,

        @Field("age")
        age: Int,

        @Field("gender")
        gender: Boolean,
    ): Call<GenericResponse>

    @POST("profile/image/upload")
    @Multipart
    fun requestUploadProfileImage(
        @Part
        file: MultipartBody.Part,

        @Part("image")
        name: RequestBody
    ): Call<GenericResponse>

    @GET("profile/image/{userid}")
    fun requestDownloadProfileImage(
        @Path("userid")
        userId: String
    ): Call<ResponseBody>

    @POST("profile")
    @FormUrlEncoded
    fun requestGetProfile(
        @Field("userid")
        userId: String
    ): Call<ProfileResponse>

    @POST("profile/stats")
    @FormUrlEncoded
    fun requestGetStatistics(
        @Field("userid")
        userId: String
    ): Call<StatisticsResponse>

    @POST("profile/option/update")
    @FormUrlEncoded
    fun requestUpdateOption(
        @Field("userid")
        userId: String,

        @Field("notice_option")
        noticeOption: Boolean,

        @Field("friend_invite_option")
        friendInviteOption: Boolean,

        @Field("plan_invite_option")
        planInviteOption: Boolean
    ): Call<GenericResponse>

    @POST("profile/option")
    @FormUrlEncoded
    fun requestGetOption(
        @Field("userid")
        userId: String
    ): Call<OptionResponse>

    @POST("profile/logout")
    @FormUrlEncoded
    fun requestLogout(
        @Field("userid")
        userId: String
    ): Call<GenericResponse>
}
package org.sehproject.sss.repository

import org.sehproject.sss.ServerApi
import org.sehproject.sss.UserInfo
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.datatype.*
import org.sehproject.sss.service.ProfileService
import org.sehproject.sss.utils.CallbackWithRetry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileRepository(private val appDatabase: AppDatabase) {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var profileService: ProfileService = retrofit.create(ProfileService::class.java)

    fun editProfile(profile: Profile, onResult: (Int) -> Unit) {
        profileService.requestEditProfile(
            profile.userId,
            profile.nickName,
            profile.name,
            profile.age,
            profile.gender,
            profile.image
        )
            .enqueue(object : CallbackWithRetry<GenericResponse>(
                profileService.requestEditProfile(
                    profile.userId,
                    profile.nickName,
                    profile.name,
                    profile.age,
                    profile.gender,
                    profile.image
                )
            ) {
                override fun onResponse(
                    call: Call<GenericResponse>,
                    response: Response<GenericResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        onResult(0)
                    } else {
                        onResult(1)
                    }
                }

                override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                    super.onFailure {
                        onResult(-1)
                    }
                }
            })
    }

    fun getProfile(userId: String, onResult: (Int, Profile?) -> Unit) {
        profileService.requestGetProfile(userId)
            .enqueue(object :
                CallbackWithRetry<ProfileResponse>(profileService.requestGetProfile(userId)) {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val profile = response.body()?.profile!!
                        onResult(0, profile)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    super.onFailure {
                        onResult(-1, null)
                    }
                }
            })
    }

    fun getStatistics(onResult: (Int, Statistics?) -> Unit) {
        profileService.requestGetStatistics(UserInfo.userId)
            .enqueue(object :
                CallbackWithRetry<StatisticsResponse>(profileService.requestGetStatistics(UserInfo.userId)) {
                override fun onResponse(
                    call: Call<StatisticsResponse>,
                    response: Response<StatisticsResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val statistics = response.body()?.statistics
                        onResult(0, statistics)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<StatisticsResponse>, t: Throwable) {
                    super.onFailure {
                        onResult(-1, null)
                    }
                }
            })
    }

    fun updateOption(
        noticeOption: Boolean,
        friendInviteOption: Boolean,
        planInviteOption: Boolean,
        onResult: (Int) -> Unit
    ) {
        profileService.requestUpdateNoticeOption(
            UserInfo.userId,
            noticeOption,
            friendInviteOption,
            planInviteOption
        )
            .enqueue(object : CallbackWithRetry<GenericResponse>(
                profileService.requestUpdateNoticeOption(
                    UserInfo.userId,
                    noticeOption,
                    friendInviteOption,
                    planInviteOption
                )
            ) {
                override fun onResponse(
                    call: Call<GenericResponse>,
                    response: Response<GenericResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        onResult(0)
                    } else {
                        onResult(1)
                    }
                }

                override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                    super.onFailure {
                        onResult(-1)
                    }
                }
            })
    }

    fun logout(onResult: (Int) -> Unit) {
        profileService.requestLogout(UserInfo.userId)
            .enqueue(object :
                CallbackWithRetry<GenericResponse>(profileService.requestLogout(UserInfo.userId)) {
                override fun onResponse(
                    call: Call<GenericResponse>,
                    response: Response<GenericResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        onResult(0)
                    } else {
                        onResult(1)
                    }
                }

                override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                    super.onFailure {
                        onResult(-1)
                    }
                }
            })
    }

    fun deleteAccount() {
        appDatabase.userDao().delete()
    }
}
package org.sehproject.sss.repository

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.sehproject.sss.ServerApi
import org.sehproject.sss.UserInfo
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.datatype.*
import org.sehproject.sss.service.ProfileService
import org.sehproject.sss.utils.CallbackWithRetry
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream

class ProfileRepository(private val appDatabase: AppDatabase) {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var profileService: ProfileService = retrofit.create(ProfileService::class.java)

    fun editProfile(profile: Profile, onResult: (Int) -> Unit) {
        val editProfileCall = profileService.requestEditProfile(
            profile.userId,
            profile.nickName,
            profile.name,
            profile.age,
            profile.gender,
        )
        editProfileCall.enqueue(object : CallbackWithRetry<GenericResponse>(editProfileCall) {
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

    fun editProfileImage(file: File, onResult: (Int) -> Unit) {
        val fileContent = ByteArray(file.length().toInt())
        val bis = BufferedInputStream(FileInputStream(file))
        val dis = DataInputStream(bis)
        dis.readFully(fileContent)

        val reqFile = RequestBody.create(MediaType.parse("image/*"), fileContent)
        val body = MultipartBody.Part.createFormData("upload", file.name, reqFile)
        val name = RequestBody.create(MediaType.parse("text/plain"), UserInfo.userId)

        val uploadProfileImageCall = profileService.requestUploadProfileImage(body, name);
        uploadProfileImageCall.enqueue(object :
            CallbackWithRetry<GenericResponse>(uploadProfileImageCall) {
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
        val getProfileCall = profileService.requestGetProfile(userId)
        getProfileCall.enqueue(object : CallbackWithRetry<ProfileResponse>(getProfileCall) {
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

    fun getProfileImage(userId: String, onResult: (Int, ByteArray?) -> Unit) {
        val getProfileImageCall = profileService.requestDownloadProfileImage(userId)
        getProfileImageCall.enqueue(object : CallbackWithRetry<ResponseBody>(getProfileImageCall) {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.body() != null) {
                    val bs = response.body()!!.byteStream()
                    val byteArray = bs.readBytes();
                    onResult(0, byteArray)
                } else {
                    onResult(1, null)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                super.onFailure {
                    onResult(-1, null)
                }
            }
        })
    }

    fun getStatistics(onResult: (Int, Statistics?) -> Unit) {
        val getStatisticsCall = profileService.requestGetStatistics(UserInfo.userId)
        getStatisticsCall.enqueue(object :
            CallbackWithRetry<StatisticsResponse>(getStatisticsCall) {
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
        val updateOptionCall = profileService.requestUpdateNoticeOption(
            UserInfo.userId,
            noticeOption,
            friendInviteOption,
            planInviteOption
        )
        updateOptionCall.enqueue(object : CallbackWithRetry<GenericResponse>(updateOptionCall) {
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
        val logoutCall = profileService.requestLogout(UserInfo.userId)
        logoutCall.enqueue(object : CallbackWithRetry<GenericResponse>(logoutCall) {
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
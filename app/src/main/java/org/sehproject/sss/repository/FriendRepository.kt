package org.sehproject.sss.repository

import org.sehproject.sss.ServerApi
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.*
import org.sehproject.sss.service.FriendService
import org.sehproject.sss.utils.CallbackWithRetry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FriendRepository {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var friendService: FriendService = retrofit.create(FriendService::class.java)

    fun searchUser(userIdOrNickName: String, onResult: (Int, List<User>?) -> Unit) {
        friendService.requestSearchUser(userIdOrNickName)
            .enqueue(object :
                CallbackWithRetry<UserListResponse>(friendService.requestSearchUser(userIdOrNickName)) {
                override fun onResponse(
                    call: Call<UserListResponse>,
                    response: Response<UserListResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val userList = response.body()?.userList
                        onResult(0, userList)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                    super.onFailure {
                        onResult(-1, null)
                    }
                }
            })
    }

    fun addFriend(friendUserId: String, onResult: (Int) -> Unit) {
        friendService.requestAddFriend(UserInfo.userId, friendUserId)
            .enqueue(object : CallbackWithRetry<GenericResponse>(
                friendService.requestAddFriend(
                    UserInfo.userId,
                    friendUserId
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

    fun deleteFriend(friendUserId: String, onResult: (Int) -> Unit) {
        friendService.requestDeleteFriend(UserInfo.userId, friendUserId)
            .enqueue(object : CallbackWithRetry<GenericResponse>(
                friendService.requestDeleteFriend(
                    UserInfo.userId,
                    friendUserId
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

    fun blockFriend(friendUserId: String, onResult: (Int) -> Unit) {
        friendService.requestBlockFriend(UserInfo.userId, friendUserId)
            .enqueue(object : CallbackWithRetry<GenericResponse>(
                friendService.requestBlockFriend(
                    UserInfo.userId,
                    friendUserId
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

    fun getFriendList(onResult: (Int, List<User>?) -> Unit) {
        friendService.requestGetFriendList(UserInfo.userId)
            .enqueue(object :
                CallbackWithRetry<UserListResponse>(friendService.requestGetFriendList(UserInfo.userId)) {
                override fun onResponse(
                    call: Call<UserListResponse>,
                    response: Response<UserListResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val userList = response.body()?.userList
                        onResult(0, userList)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                    super.onFailure {
                        onResult(-1, null)
                    }
                }
            })
    }
}
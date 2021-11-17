package org.sehproject.sss.repository

import org.sehproject.sss.ServerApi
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.datatype.Account
import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.datatype.User
import org.sehproject.sss.datatype.UserResponse
import org.sehproject.sss.service.UserService
import org.sehproject.sss.utils.CallbackWithRetry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository(private val appDatabase: AppDatabase) {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var userService: UserService = retrofit.create(UserService::class.java)

    fun login(userId: String, password: String, onResult: (Int, String?) -> Unit) {
        userService.requestLogin(userId, password)
            .enqueue(object :
                CallbackWithRetry<UserResponse>(userService.requestLogin(userId, password)) {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val nickName = response.body()?.user?.nickName
                        onResult(0, nickName)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    super.onFailure {
                        onResult(-1, null)
                    }
                }
            })
    }

    fun register(userId: String, password: String, nickName: String, onResult: (Int) -> Unit) {
        userService.requestRegister(userId, password, nickName)
            .enqueue(object : CallbackWithRetry<GenericResponse>(
                userService.requestRegister(
                    userId,
                    password,
                    nickName
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

    fun getUser(userId: String, onResult: (Int, User?) -> Unit) {
        userService.requestGetUser(userId)
            .enqueue(object :
                CallbackWithRetry<UserResponse>(userService.requestGetUser(userId)) {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    val code = response.body()?.code
                    if (code == 0) {
                        val user = response.body()?.user
                        onResult(0, user)
                    } else {
                        onResult(1, null)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    super.onFailure {
                        onResult(-1, null)
                    }
                }
            })
    }

    fun getSavedAccount(): Account? {
        return appDatabase.userDao().select()
    }

    fun saveAccount(account: Account) {
        appDatabase.userDao().insert(account)
    }
}
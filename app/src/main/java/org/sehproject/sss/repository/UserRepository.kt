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
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository(private val appDatabase: AppDatabase) {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ServerApi.DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var userService: UserService = retrofit.create(UserService::class.java)

    fun login(userId: String, password: String, token: String, onResult: (Int, String?) -> Unit) {
        val loginCall = userService.requestLogin(userId, password, token)
        loginCall.enqueue(object : CallbackWithRetry<UserResponse>(loginCall) {
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

    fun apiLogin(apiId: String, onResult: (Int, String?, String?) -> Unit) {
        val apiLoginCall = userService.requestApiLogin(apiId)
        apiLoginCall.enqueue(object : CallbackWithRetry<UserResponse>(apiLoginCall) {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                val code = response.body()?.code
                if (code == 0) {
                    val userId = response.body()?.user?.userId
                    val nickName = response.body()?.user?.nickName
                    onResult(0, nickName, userId)
                } else {
                    onResult(1, null, null)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                super.onFailure {
                    onResult(-1, null, null)
                }
            }
        })
    }

    fun apiRegister(userId: String, nickName: String, apiId: String, onResult: (Int) -> Unit) {
        val apiRegisterCall = userService.requestApiRegister(userId, nickName, apiId)
        apiRegisterCall.enqueue(object : CallbackWithRetry<GenericResponse>(apiRegisterCall) {
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

    fun register(userId: String, password: String, nickName: String, onResult: (Int) -> Unit) {
        val registerCall = userService.requestRegister(userId, password, nickName)
        registerCall.enqueue(object : CallbackWithRetry<GenericResponse>(registerCall) {
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
        val getUserCall = userService.requestGetUser(userId)
        getUserCall.enqueue(object :
            CallbackWithRetry<UserResponse>(getUserCall) {
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

    fun deleteAccount() {
        appDatabase.userDao().delete()
    }
}
package org.sehproject.sss.repository

import org.sehproject.sss.ServerApi
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.datatype.Account
import org.sehproject.sss.datatype.GenericResponse
import org.sehproject.sss.service.UserService
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

    fun login(userId: String, password: String, onResult: (Int) -> Unit) {
        userService.loginRequest(userId, password)
            .enqueue(object : Callback<GenericResponse> {
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
                    onResult(-1)
                }
            })
    }

    fun register(userId: String, password: String, nickName: String, onResult: (Int) -> Unit) {
        userService.registerRequest(userId, password, nickName)
            .enqueue(object : Callback<GenericResponse> {
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
                    onResult(-1)
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
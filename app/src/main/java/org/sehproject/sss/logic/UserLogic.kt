package org.sehproject.sss.logic

import android.util.Log
import org.sehproject.sss.datatype.Account
import org.sehproject.sss.datatype.User
import org.sehproject.sss.viewmodel.UserViewModel

class UserLogic(val userViewModel: UserViewModel) {
    fun onLoginClick(user: Account)
    {
        userViewModel.onLogin(user)
        userViewModel.loginEvent.call()
    }

    fun onRegisterClick()
    {
        Log.d("TAG","erer")
        userViewModel.registerEvent.call()
    }
}
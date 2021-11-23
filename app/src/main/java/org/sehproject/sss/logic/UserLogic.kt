package org.sehproject.sss.logic

import android.util.Log
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.AccountXML
import org.sehproject.sss.viewmodel.UserViewModel

class UserLogic(val userViewModel: UserViewModel) {
    val userRepository = userViewModel.userRepository

    fun onLoginClick(user: AccountXML)
    {
        Log.d("TAG", user.userId)
        userViewModel.loginEvent.call()

//        userRepository.login(user.userId, user.password) { code: Int, nickName: String? ->
//            if (code == 0) {
//                updateUI(nickName!!)
//                userViewModel.loginEvent.call()
//            }
//        }
    }

    fun onRegisterClick()
    {
        userViewModel.registerEvent.call()
    }

    fun onRegisterCompleteClick(userId: String, password: String, confirmPassword: String, nickName: String)
    {
        if (password == confirmPassword) {
            userRepository.register(userId, password, nickName) { code: Int ->
                if(code == 0) {
                    userViewModel.registerCompleteEvent.call()
                }
            }
        }
    }

    fun updateUI(name: String) {
        UserInfo.isLogin = true
        UserInfo.userName = name
        // userViewModel.isLogin.value = true// thread 사용시 바꿔야함
    }
}
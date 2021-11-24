package org.sehproject.sss.logic

import android.util.Log
import org.json.JSONObject
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.Account
import org.sehproject.sss.datatype.AccountXML
import org.sehproject.sss.viewmodel.UserViewModel

class UserLogic(val userViewModel: UserViewModel) {
    val userRepository = userViewModel.userRepository
    val RC_SIGN_IN = 9001

    // local
    fun onLoginClick(user: AccountXML) {
        Log.d("TAG", user.userId)
        userViewModel.loginEvent.call()

//        userRepository.login(user.userId, user.password) { code: Int, nickName: String? ->
//            if (code == 0) {
//                if(userRepository.getSavedAccount() == null)
//                    userRepository.saveAccount(Account(user.userId, user.password, "", 0))
//                //updateUI(nickName!!)
//                userViewModel.loginEvent.call()
//            }
//        }
    }

    fun onGoogleLoginClick() {
        val signInIntent = userViewModel.googleSignInClient.signInIntent
        userViewModel.googleLoginEvent.sendEvent {
            startActivityForResult(
                signInIntent,
                RC_SIGN_IN
            )
        }
    }

    fun onGoogleRegisterClick() {
        val signInIntent = userViewModel.googleSignInClient.signInIntent
        userViewModel.googleRegisterEvent.sendEvent {
            startActivityForResult(
                signInIntent,
                RC_SIGN_IN
            )
        }
    }

    fun naverLogInCallback(result: String) {
        val jsonObject = JSONObject(result)
        val responseObject = JSONObject(jsonObject.getString("response"))
        val id = responseObject.getString("id")
        updateUserInfo(id)

        userViewModel.loginEvent.call()
    }

    fun naverRegisterCallback(result: String) {
        val jsonObject = JSONObject(result)
        val responseObject = JSONObject(jsonObject.getString("response"))
        val id = responseObject.getString("id")
        updateUserInfo(id)

        userViewModel.registerApiEvent.value = id
    }


    fun onRegisterClick() {
        userViewModel.registerEvent.call()
    }

    // local
    fun onRegisterCompleteClick(
        userId: String,
        password: String,
        confirmPassword: String,
        nickName: String,
        apiId: String
    ) {
        Log.d("TAG", "userId: $userId\npassword: $password\nnickName: $nickName\napiId: $apiId")
        if (password == confirmPassword) {
            userRepository.register(userId, password, nickName) { code: Int ->
                if (code == 0) {
                    userViewModel.registerCompleteEvent.call()
                }
            }
        }
    }

    fun updateUserInfo(id: String) {
        UserInfo.isLogin = true
        UserInfo.userId = id
        // userViewModel.isLogin.value = true// thread 사용시 바꿔야함
    }
}
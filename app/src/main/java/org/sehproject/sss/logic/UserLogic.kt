package org.sehproject.sss.logic

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.nhn.android.naverlogin.data.OAuthLoginState
import org.json.JSONObject
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.Account
import org.sehproject.sss.datatype.AccountXML
import org.sehproject.sss.viewmodel.UserViewModel

class UserLogic(val userViewModel: UserViewModel) {
    val RC_SIGN_IN = 9001


    fun checkLogin(user: AccountXML, naverLoginState: OAuthLoginState) {
        val auth = FirebaseAuth.getInstance()
        val account = userViewModel.userRepository.getSavedAccount()

        if (account != null) {
            when (account.flag) {
                0 -> userViewModel.userRepository.login(
                    account.userId,
                    account.password, "token"
                ) { code, nickName ->
                    if (code == 0) {
                        userViewModel.userLogic.updateUserInfo(account.userId, account.password, 0)
                    } else if(code == 1) {
                        userViewModel.userRepository.deleteAccount()
                    }
                }
                1 -> //Google
                {
                    //userViewModel.loginEvent.call()
                    userViewModel.userRepository.apiLogin(account.apiId) { code, nickName ->
                        if (code == 0) {
                            if (auth.currentUser != null) {
                                userViewModel.userLogic.updateUserInfo(account.apiId, null, 1)
                                userViewModel.loginEvent.call()
                            }
                        } else {
                            // api 로그인 실패
                        }
                    }
                }
                2 -> {//Naver
                    // api 관련 로직
                    userViewModel.userRepository.apiLogin(account.apiId) { code, nickName ->
                        if (code == 0) {
                            if (!(OAuthLoginState.NEED_LOGIN == naverLoginState ||
                                        OAuthLoginState.NEED_INIT == naverLoginState)) {
                                userViewModel.userLogic.updateUserInfo(account.apiId, null, 2)
                            }

                        } else {
                            // api 로그인 실패
                        }
                    }
                }
            }
        }
    }
    // local
    fun onLoginClick(user: AccountXML) {
        userViewModel.userRepository.login(
            user.userId,
            user.password,
            "token"
        ) { code: Int, nickName: String? ->
            if (code == 0) {
                updateUserInfo(user.userId, user.password, 0)
            } else if (code == 1) {
                // 로그인 실패 메시지
            }
        }
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
        updateUserInfo(id, null, 2)
    }

    fun naverRegisterCallback(result: String) {
        val jsonObject = JSONObject(result)
        val responseObject = JSONObject(jsonObject.getString("response"))
        val id = responseObject.getString("id")

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
        if (password == confirmPassword) {
            if(apiId == "") {
                userViewModel.userRepository.register(userId, password, nickName) { code: Int ->
                    if (code == 0)
                        userViewModel.registerCompleteEvent.call()
                }
            }
            else
                userViewModel.userRepository.apiRegister(userId, nickName, apiId) { code: Int ->
                    if(code == 0)
                        userViewModel.registerCompleteEvent.call()
                }
        }
    }

    fun updateUserInfo(id: String, password: String?, flag: Int) {
        UserInfo.isLogin = true
        UserInfo.userId = id
        when (flag) {
            0 -> userViewModel.userRepository.saveAccount(Account(id, password!!, "", flag))
            1, 2 -> userViewModel.userRepository.saveAccount(Account("", "", id, flag))
        }

        userViewModel.loginEvent.call()
    }
}
package org.sehproject.sss.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.UserInfo
import org.sehproject.sss.dao.NaverAsyncTask
import org.sehproject.sss.datatype.User
import org.sehproject.sss.repository.LoginRepository
import org.sehproject.sss.utils.ActivityNavigation
import org.sehproject.sss.utils.LiveMessageEvent
import org.sehproject.sss.utils.SingleLiveEvent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import org.json.JSONObject

class LoginViewModel : ViewModel() {
    val btnSelected = MutableLiveData(true)
    val isLogin = MutableLiveData(false)
    val googleLoginEvent = LiveMessageEvent<ActivityNavigation>()
    val cheatEvent = SingleLiveEvent<Any>()
    private val loginRepository = LoginRepository.get()
    private lateinit var googleSignInClient: GoogleSignInClient
    val RC_SIGN_IN = 9001

    fun setGoogleClient(_googleSignInClient: GoogleSignInClient) {
        googleSignInClient = _googleSignInClient
    }
    fun onLogin(user: User) {
        Log.d("tag", user.toString())
        if (checkValidate(user.email, user.password)) {
            val nickname = loginRepository.login(user.email, user.password)
            if (nickname.isNotEmpty()) {
                updateUI(nickname)
            }
        }
    }

    fun onCheat() {
        cheatEvent.call()
    }

    private fun checkValidate(email: String, password: String): Boolean = true

    fun naverLogInCallback(result: String) {
        val jsonObject = JSONObject(result)
        val responseObject = JSONObject(jsonObject.getString("response"))
        val email = responseObject.getString("email")
        updateUI(email)
    }

    fun updateUI(name: String) {
        UserInfo.isLogin = true
        UserInfo.userName = name
        isLogin.value = true// thread 사용시 바꿔야함
    }
    fun onGoogleLogin() {
        val signInIntent = googleSignInClient.signInIntent
        googleLoginEvent.sendEvent { startActivityForResult(signInIntent, RC_SIGN_IN) }
    }

    //nested class: outer class의 member를 사용할 수 없다.
    class NaverLoginHandler(private val context: Context,
                            private val mOAuthLoginModule: OAuthLogin,
                            private val callback: (String) -> Unit) : OAuthLoginHandler() {
        override fun run(p0: Boolean) {
            if (p0) {
                val accessToken = mOAuthLoginModule.getAccessToken(context)
                val refreshToken = mOAuthLoginModule.getRefreshToken(context)
                val expiresAt = mOAuthLoginModule.getExpiresAt(context)
                val tokenType = mOAuthLoginModule.getTokenType(context)
                //Logging에 사용할 수 있음.
                val task = NaverAsyncTask(
                        context,
                        mOAuthLoginModule,
                        accessToken,
                        callback
                )
                task.execute()
            } else {
                val errorCode = mOAuthLoginModule.getLastErrorCode(context).code
                Toast.makeText(context, "errorCode: $errorCode", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
package org.sehproject.sss.utils

import android.content.Context
import android.widget.Toast
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler

class NaverLoginHandler(
    private val context: Context,
    private val mOAuthLoginModule: OAuthLogin,
    private val callback: (String) -> Unit
) : OAuthLoginHandler() {
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
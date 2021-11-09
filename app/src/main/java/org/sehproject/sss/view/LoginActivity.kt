package org.sehproject.sss.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import org.sehproject.sss.R
import org.sehproject.sss.UserInfo
import org.sehproject.sss.databinding.ActivityLoginBinding
import org.sehproject.sss.datatype.User
import org.sehproject.sss.utils.ActivityNavigation
import org.sehproject.sss.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.nhn.android.naverlogin.OAuthLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.sehproject.sss.repository.LoginRepository.Companion.get


class LoginActivity : AppCompatActivity(), ActivityNavigation {

    lateinit var loginBinding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val user = User()
    private val loginViewModel: LoginViewModel by lazy {
            ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginBinding.viewmodel = loginViewModel
        loginBinding.user = user
        initObservers()
        initNaverLogin()
        initGoogleLogin()
    }

    private fun initObservers() {
        val transact = {
            Toast.makeText(this, "welcome, ${UserInfo.userName}", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        loginViewModel.isLogin.observe(this, Observer {
            if (it) {
                transact()
            }
        })
        loginViewModel.cheatEvent.observe(this, {
            val editText = EditText(this)

            val builder = AlertDialog.Builder(this)
            builder.setTitle("메모")
            builder.setView(editText)
            builder.setPositiveButton("입력", DialogInterface.OnClickListener{
                dialog, which ->
                Toast.makeText(applicationContext, editText.text, Toast.LENGTH_SHORT).show()
            })
            builder.show()
        })
    }

    private fun initNaverLogin() {
        val mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(
            this,
            "oG9t60dhDXU6bNYSbpdK",
            "mL_2dNGHcu",
            "LoginMine"
        )
        val mOAuthLoginHandler =
            LoginViewModel.NaverLoginHandler(this, mOAuthLoginModule, loginViewModel::naverLogInCallback)
        loginBinding.buttonNaverLogin.setOAuthLoginHandler(mOAuthLoginHandler)
        loginBinding.buttonNaverLogin.setBgResourceId(R.drawable.btn_ag)
    }

    private fun initGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("719717179769-tu7oe94t8beedgs3ee0cgcb5kebe5rqc.apps.googleusercontent.com")
                .requestEmail()
                .build()
        Log.d("tag", "...${gso.toString()}" )
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()
        loginViewModel.setGoogleClient(googleSignInClient)
        loginViewModel.googleLoginEvent.setEventReceiver(this, this)
        loginBinding.buttonGoogleLogin.setOnClickListener {
            Log.d("TAG", "!!")
            loginViewModel.onGoogleLogin()
        }
        val textView = loginBinding.buttonGoogleLogin.getChildAt(0) as TextView
        textView.text = getString(R.string.google_login)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == loginViewModel.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("tag", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.d("tag", "Google sign in failed", e)
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("tag", "signInWithCredential:success")
                        val user = auth.currentUser
                        loginViewModel.updateUI(user!!.email!!)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("tag", "signInWithCredential:failure", task.exception)
                    }
                }
    }

}
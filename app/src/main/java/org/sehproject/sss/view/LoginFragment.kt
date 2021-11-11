/*
package org.sehproject.sss.view

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.nhn.android.naverlogin.OAuthLogin
import org.sehproject.sss.R
import org.sehproject.sss.UserInfo
import org.sehproject.sss.databinding.ActivityLoginBinding
import org.sehproject.sss.datatype.User
import org.sehproject.sss.utils.ActivityNavigation
import org.sehproject.sss.viewmodel.LoginViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var loginBinding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val user = User()
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginBinding = DataBindingUtil.setContentView((activity as MainActivity), R.layout.fragment_login)
        loginBinding.viewmodel = loginViewModel
        loginBinding.user = user
        initObservers()
        initNaverLogin()
        initGoogleLogin()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun initObservers() {
        val transact = {
            Toast.makeText(getActivity(), "welcome, ${UserInfo.userName}", Toast.LENGTH_LONG).show()
            val intent = Intent((activity as MainActivity), MainActivity::class.java)
            startActivity(intent)
        }
        loginViewModel.isLogin.observe(this, Observer {
            if (it) {
                transact()
            }
        })
        loginViewModel.cheatEvent.observe(this, {
            val editText = EditText((activity as MainActivity))

            val builder = AlertDialog.Builder((activity as MainActivity))
            builder.setTitle("메모")
            builder.setView(editText)
            builder.setPositiveButton("입력", DialogInterface.OnClickListener{
                    dialog, which ->
                Toast.makeText(getActivity()?.getApplicationContext(), editText.text, Toast.LENGTH_SHORT).show()
            })
            builder.show()
        })
    }

    private fun initNaverLogin() {
        val mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(
            (activity as MainActivity),
            "a7XW5Z1zpCBPzzIUT1FS",
            "HtKAgnWhV6",
            "SSS"
        )
        val mOAuthLoginHandler =
            LoginViewModel.NaverLoginHandler((activity as MainActivity), mOAuthLoginModule, loginViewModel::naverLogInCallback)
        loginBinding.buttonNaverLogin.setOAuthLoginHandler(mOAuthLoginHandler)
        loginBinding.buttonNaverLogin.setBgResourceId(R.drawable.btn_ag)
    }

    private fun initGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("719717179769-tu7oe94t8beedgs3ee0cgcb5kebe5rqc.apps.googleusercontent.com")
            .requestEmail()
            .build()
        Log.d("tag", "...${gso.toString()}" )
        val googleSignInClient = GoogleSignIn.getClient((activity as MainActivity), gso)
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
            .addOnCompleteListener((activity as MainActivity)) { task ->
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
 */
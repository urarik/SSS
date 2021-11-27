package org.sehproject.sss.view

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.R
// import org.sehproject.sss.databinding.ActivityLoginBinding
import org.sehproject.sss.utils.ActivityNavigation
import org.sehproject.sss.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.nhn.android.idp.common.util.DeviceAppInfo.getPackageName
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.data.OAuthLoginState
import org.sehproject.sss.UserInfo
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentLoginBinding
import org.sehproject.sss.datatype.Account
import org.sehproject.sss.datatype.AccountXML
import org.sehproject.sss.utils.UserViewModelFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class LoginFragment : Fragment(), ActivityNavigation {
    lateinit var loginBinding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mOAuthLoginModule: OAuthLogin
    private val user = AccountXML("", "", "", 0)
    private val userViewModel: UserViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, UserViewModelFactory(appDatabase)).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        loginBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container, false)
        loginBinding.userLogic = userViewModel.userLogic
        loginBinding.user = user
        initObservers()
        initNaverLogin()
        initGoogleLogin()
        getHashKey()

        return loginBinding.root
    }

    override fun onStart() {
        super.onStart()
        val auth = FirebaseAuth.getInstance()

        userViewModel.userLogic.checkLogin(user, mOAuthLoginModule.getState(context))

        auth.currentUser?: run {
            auth.signInAnonymously()
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        //Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInAnonymously:success")
                        val user = auth.currentUser
                        //updateUI(user)
                    } else {
                        //If sign in fails, display a message to the user.
                        Log.d("TAG", "signInAnonymously:failure", task.exception)
                        //updateUI(null)
                    }
                }
        }
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo =
                requireContext().packageManager.getPackageInfo(
                    getPackageName(context),
                    PackageManager.GET_SIGNATURES
                )
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }


    private fun initObservers() {
        val navController = findNavController()
        userViewModel.loginEvent.observe(viewLifecycleOwner, Observer {
            val mainIntent = Intent(context, MainActivity::class.java)
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(mainIntent)
            requireActivity().finish()
        })
        userViewModel.registerEvent.observe(viewLifecycleOwner, Observer {
            navController.navigate(R.id.registerChooserFragment, null)
        })
    }

    private fun initNaverLogin() {
        mOAuthLoginModule = OAuthLogin.getInstance().also {
            it.init(
                context,
                "a7XW5Z1zpCBPzzIUT1FS",
                "HtKAgnWhV6",
                "SSS"
            )
        }

        val mOAuthLoginHandler =
            UserViewModel.NaverLoginHandler(
                requireContext(),
                mOAuthLoginModule,
                userViewModel.userLogic::naverLogInCallback
            )
        loginBinding.buttonNaverLogin.setOAuthLoginHandler(mOAuthLoginHandler)
        loginBinding.buttonNaverLogin.setBgResourceId(R.drawable.btn_ag)
    }

    private fun initGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("719717179769-tu7oe94t8beedgs3ee0cgcb5kebe5rqc.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = FirebaseAuth.getInstance()
        userViewModel.setGoogleClient(googleSignInClient)

        loginBinding.buttonGoogleLogin.setOnClickListener {
            userViewModel.userLogic.onGoogleLoginClick()
        }
        userViewModel.googleLoginEvent.setEventReceiver(this, this)
        val textView = loginBinding.buttonGoogleLogin.getChildAt(0) as TextView
        textView.text = getString(R.string.google_login)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == userViewModel.userLogic.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("tag", "firebaseAuthWithGoogle:" + account.email)
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
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("tag", "signInWithCredential:success")
                    val uid = auth.currentUser!!.uid
                    userViewModel.userLogic.apiLogic(uid, 1)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("tag", "signInWithCredential:failure", task.exception)
                }
            }
    }

}
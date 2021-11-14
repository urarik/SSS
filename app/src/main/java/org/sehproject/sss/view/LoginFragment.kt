package org.sehproject.sss.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.R
import org.sehproject.sss.UserInfo
// import org.sehproject.sss.databinding.ActivityLoginBinding
import org.sehproject.sss.datatype.User
import org.sehproject.sss.utils.ActivityNavigation
import org.sehproject.sss.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.nhn.android.naverlogin.OAuthLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.sehproject.sss.databinding.FragmentLoginBinding
import org.sehproject.sss.datatype.Account
import org.sehproject.sss.repository.LoginRepository.Companion.get


class LoginFragment : Fragment(), ActivityNavigation {

    lateinit var loginBinding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private val user = Account("", "")
    private val userViewModel: UserViewModel by lazy {
            ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        loginBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container, false)
        loginBinding.userLogic = userViewModel.userLogic
        loginBinding.user = user
        initObservers()
        initNaverLogin()
        initGoogleLogin()

        return loginBinding.root
    }

    private fun initObservers() {
        userViewModel.isLogin.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(R.id.planListFragment, null)
            }
        })
        userViewModel.registerEvent.observe(viewLifecycleOwner, Observer {
            Log.d("TAG", "eeeeeeee")
            findNavController().navigate(R.id.registerFragment, null)
        })
        userViewModel.cheatEvent.observe(this, {
            val editText = EditText(context)

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("메모")
            builder.setView(editText)
            builder.setPositiveButton("입력", DialogInterface.OnClickListener{
                dialog, which ->
                //Toast.makeText(applicationContext, editText.text, Toast.LENGTH_SHORT).show()
            })
            builder.show()
        })
    }

    private fun initNaverLogin() {
        val mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(
            context,
            "a7XW5Z1zpCBPzzIUT1FS",
            "HtKAgnWhV6",
            "SSS"
        )

        val mOAuthLoginHandler =
            UserViewModel.NaverLoginHandler(requireContext(), mOAuthLoginModule, userViewModel::naverLogInCallback)
        loginBinding.buttonNaverLogin.setOAuthLoginHandler(mOAuthLoginHandler)
        loginBinding.buttonNaverLogin.setBgResourceId(R.drawable.btn_ag)
    }

    private fun initGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("719717179769-tu7oe94t8beedgs3ee0cgcb5kebe5rqc.apps.googleusercontent.com")
                .requestEmail()
                .build()
        Log.d("tag", "...${gso.toString()}" )
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = FirebaseAuth.getInstance()
        userViewModel.setGoogleClient(googleSignInClient)
        userViewModel.googleLoginEvent.setEventReceiver(this, this)
        loginBinding.buttonGoogleLogin.setOnClickListener {
            Log.d("TAG", "!!")
            userViewModel.onGoogleLogin()
        }
        val textView = loginBinding.buttonGoogleLogin.getChildAt(0) as TextView
        textView.text = getString(R.string.google_login)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == userViewModel.RC_SIGN_IN) {
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
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("tag", "signInWithCredential:success")
                        val user = auth.currentUser
                        userViewModel.updateUI(user!!.email!!)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("tag", "signInWithCredential:failure", task.exception)
                    }
                }
    }

}
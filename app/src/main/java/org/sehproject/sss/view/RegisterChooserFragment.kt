package org.sehproject.sss.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.nhn.android.naverlogin.OAuthLogin
import org.sehproject.sss.R
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentDialogRegisterBinding
import org.sehproject.sss.utils.ActivityNavigation
import org.sehproject.sss.utils.UserViewModelFactory
import org.sehproject.sss.viewmodel.UserViewModel




class RegisterChooserFragment: Fragment(), ActivityNavigation {
    private val userViewModel: UserViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, UserViewModelFactory(appDatabase)).get(UserViewModel::class.java)
    }
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val registerDialogBinding : FragmentDialogRegisterBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_dialog_register, container, false)
        registerDialogBinding.userLogic = userViewModel.userLogic

        initGoogleLogin(registerDialogBinding)
        initNaverLogin(registerDialogBinding)
        initObserver()
        return registerDialogBinding.root
        }
    private fun initObserver() {
        val navController = findNavController()
        userViewModel.registerApiEvent.observe(viewLifecycleOwner, {
            val action = RegisterChooserFragmentDirections.actionRegisterChooserFragmentToRegisterFragment(it)
            navController.navigate(action)
        })
        userViewModel.registerEvent.observe(viewLifecycleOwner, {
            val action = RegisterChooserFragmentDirections.actionRegisterChooserFragmentToRegisterFragment("")
            navController.navigate(action)
        })

    }

    private fun initNaverLogin(regDialogRegisterBinding: FragmentDialogRegisterBinding) {
        val mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(
            requireActivity().baseContext,
            "a7XW5Z1zpCBPzzIUT1FS",
            "HtKAgnWhV6",
            "SSS"
        )

        val mOAuthLoginHandler =
            UserViewModel.NaverLoginHandler(
                requireActivity().baseContext,
                mOAuthLoginModule,
                userViewModel.userLogic::naverRegisterCallback
            )
        regDialogRegisterBinding.buttonNaverRegister.setOAuthLoginHandler(mOAuthLoginHandler)
        regDialogRegisterBinding.buttonNaverRegister.setBgResourceId(R.drawable.btn_ag)
    }

    private fun initGoogleLogin(regDialogRegisterBinding: FragmentDialogRegisterBinding) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("719717179769-tu7oe94t8beedgs3ee0cgcb5kebe5rqc.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = FirebaseAuth.getInstance()
        userViewModel.setGoogleClient(googleSignInClient)


        regDialogRegisterBinding.buttonGoogleRegister.setOnClickListener {
            userViewModel.userLogic.onGoogleRegisterClick()
        }
        userViewModel.googleRegisterEvent.setEventReceiver(this, this)

        val textView = regDialogRegisterBinding.buttonGoogleRegister.getChildAt(0) as TextView
        textView.text = getString(R.string.google_login)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == userViewModel.userLogic.RC_SIGN_IN) {
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
                    userViewModel.registerApiEvent.value = uid
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("tag", "signInWithCredential:failure", task.exception)
                }
            }
    }
}
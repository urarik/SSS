package org.sehproject.sss

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.nhn.android.naverlogin.OAuthLogin
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.FragmentProfileBinding
import org.sehproject.sss.datatype.Profile
import org.sehproject.sss.utils.ProfileViewModelFactory
import org.sehproject.sss.utils.UserViewModelFactory
import org.sehproject.sss.view.LoginActivity
import org.sehproject.sss.view.MainActivity
import org.sehproject.sss.viewmodel.ProfileViewModel
import org.sehproject.sss.viewmodel.UserViewModel

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(requireContext())!!
        ViewModelProvider(this, ProfileViewModelFactory(appDatabase)).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileBinding: FragmentProfileBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_profile,
            container,
            false
        )
        profileBinding.profileLogic = profileViewModel.profileLogic
        profileBinding.profile = Profile()

        initObserver(profileBinding)

        //for Google logout
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("719717179769-tu7oe94t8beedgs3ee0cgcb5kebe5rqc.apps.googleusercontent.com")
            .requestEmail()
            .build()
        profileViewModel.   googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        profileViewModel.setProfile(UserInfo.userId)
        return profileBinding.root
    }

    fun initObserver(profileBinding: FragmentProfileBinding) {
        val navController = findNavController()
        profileViewModel.editProfileEvent.observe(viewLifecycleOwner, {
            val action = ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment(it)
            navController.navigate(action)
        })

        profileViewModel.selectOptionEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.settingFragment)
        })

        profileViewModel.viewStatisticsEvent.observe(viewLifecycleOwner, {
            navController.navigate(R.id.statisticsDialogFragment)
        })

        profileViewModel.logoutEvent.observe(viewLifecycleOwner, {
            val loginIntent = Intent(context, LoginActivity::class.java)
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(loginIntent)
            requireActivity().finish()
        })

        profileViewModel.profileLiveData.observe(viewLifecycleOwner, {
            profileBinding.profile = it
        })

        profileViewModel.imageBitmapLiveData.observe(viewLifecycleOwner, {
            val imageView = profileBinding.imageView
            imageView.setImageBitmap(profileViewModel.imageBitmapLiveData.value)
        })

        profileViewModel.naverLogoutEvent.observe(viewLifecycleOwner, {
            val mOAuthLoginModule = OAuthLogin.getInstance()
            mOAuthLoginModule.logout(context)
        })
    }
}
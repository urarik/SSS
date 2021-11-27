package org.sehproject.sss.logic

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.DocumentsProvider
import android.util.Log
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.nhn.android.naverlogin.OAuthLogin
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.datatype.Profile
import org.sehproject.sss.utils.UserViewModelFactory
import org.sehproject.sss.viewmodel.ProfileViewModel
import org.sehproject.sss.viewmodel.UserViewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URI
import javax.xml.parsers.DocumentBuilder

class ProfileLogic(val profileViewModel: ProfileViewModel) {
    fun onEditProfileClick(userId: String) {
        profileViewModel.editProfileEvent.value = userId
    }

    fun onEditProfileCompleteClick(profile: Profile) {
        val file = File(profileViewModel.imageUri?.path)

            profileViewModel.profileRepository.editProfile(profile) { code: Int ->
            if (code == 0) {
                profileViewModel.profileRepository.editProfileImage(file, profileViewModel.imageStream!!) { code1: Int ->
                    if (code1 == 0) {
                        profileViewModel.imageFile = file
                        profileViewModel.editProfileCompleteEvent.call()
                    }
                }
            }
        }
    }

    fun onUploadImageClick() {
        profileViewModel.uploadImageEvent.call()
    }

    fun onViewStatisticsClick() {
        profileViewModel.viewStatisticsEvent.call()
    }

    fun onViewStatisticsExitClick() {
        profileViewModel.viewStatisticsExitEvent.call()
    }

    fun onSelectOptionClick() {
        profileViewModel.selectOptionEvent.call()
    }

    fun onSelectNoticeOptionClick(option: Boolean) {
        profileViewModel.profileRepository.updateOption(option, profileViewModel.inviteFriendOptionLiveData.value!!, profileViewModel.invitePlanOptionLiveData.value!!) { code: Int ->
            if(code == 0) {

            }
        }
    }

    fun onSelectInviteFriendOptionClick(option: Boolean) {
        profileViewModel.profileRepository.updateOption(profileViewModel.noticeOptionLiveData.value!!, option, profileViewModel.invitePlanOptionLiveData.value!!) { code: Int ->
            if(code == 0) {

            }
        }
    }

    fun onSelectInvitePlanOptionClick(option: Boolean) {
        profileViewModel.profileRepository.updateOption(profileViewModel.noticeOptionLiveData.value!!, profileViewModel.inviteFriendOptionLiveData.value!!, option) { code: Int ->
            if(code == 0) {

            }
        }
    }

    fun onLogoutClick() {
        profileViewModel.profileRepository.logout { code: Int ->
            if (code == 0) {
                val account = profileViewModel.profileRepository.getSavedAccount()
                val auth = FirebaseAuth.getInstance()
                when (account!!.flag) {
                    1 -> { auth.signOut()
                        profileViewModel.googleSignInClient.signOut()
                            .addOnCompleteListener(OnCompleteListener {
                        })
                    }
                    // 2 ->
                }
                profileViewModel.profileRepository.deleteAccount()
                profileViewModel.logoutEvent.call()
            }
        }
    }


}
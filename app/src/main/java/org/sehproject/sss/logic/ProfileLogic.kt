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
import androidx.lifecycle.MutableLiveData
import org.sehproject.sss.datatype.Profile
import org.sehproject.sss.viewmodel.ProfileViewModel
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
        profileViewModel.imageFile = file
        if (profileViewModel.imageFile != null) {
            Log.d("TAG", "success")
        }
        profileViewModel.editProfileCompleteEvent.call()

//            profileRepository.editProfile(profile) { code: Int ->
//            if (code == 0) {
//                profileRepository.editProfileImage(file) { code: Int ->
//                    if (code == 0) {
//                        val file = File(profileViewModel.imageUri?.path)
//                        profileViewModel.imageFile = file
//                        profileViewModel.editProfileCompleteEvent.call()
//                    }
//                }
//            }
//        }
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
        profileViewModel.noticeOptionLiveData.value = option

//        profileRepository.updateOption(option, profileViewModel.inviteFriendOptionLiveData.value!!, profileViewModel.invitePlanOptionLiveData.value!!) { code: Int ->
//            if(code == 0) {
//                TODO("옵션 설정 후")
//            }
//        }
    }

    fun onSelectInviteFriendOptionClick(option: Boolean) {
        profileViewModel.inviteFriendOptionLiveData.value = option

//        profileRepository.updateOption(profileViewModel.noticeOptionLiveData.value!!, option, profileViewModel.invitePlanOptionLiveData.value!!) { code: Int ->
//            if(code == 0) {
//                TODO("옵션 설정 후")
//            }
//        }
    }

    fun onSelectInvitePlanOptionClick(option: Boolean) {
        profileViewModel.invitePlanOptionLiveData.value = option

//        profileRepository.updateOption(profileViewModel.noticeOptionLiveData.value!!, profileViewModel.inviteFriendOptionLiveData.value!!, option) { code: Int ->
//            if(code == 0) {
//                TODO("옵션 설정 후")
//            }
//        }
    }

    fun onLogoutClick() {
        profileViewModel.profileRepository.logout { code: Int ->
            if (code == 0) {
                val account = profileViewModel.profileRepository.getSavedAccount()
                when (account!!.flag) {
                    // 1 ->
                    // 2 ->
                }
                profileViewModel.profileRepository.deleteAccount()
                profileViewModel.logoutEvent.call()
            }
        }
    }
}
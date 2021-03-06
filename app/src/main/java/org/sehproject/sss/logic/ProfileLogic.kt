package org.sehproject.sss.logic

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.Profile
import org.sehproject.sss.viewmodel.ProfileViewModel
import java.io.File


class ProfileLogic(val profileViewModel: ProfileViewModel) {

    fun onEditProfileClick(userId: String) {
        profileViewModel.editProfileEvent.value = userId
    }

    fun onEditProfileCompleteClick(profile: Profile) {
        val path = profileViewModel.imageUri?.path

            profileViewModel.profileRepository.editProfile(profile) { code: Int ->
            if (code == 0) {
                path?. run {
                    val file = File(path)
                    profileViewModel.profileRepository.editProfileImage(file,
                        profileViewModel.imageStream!!,
                        profileViewModel.imageLength,
                        profileViewModel.imageExtension) { code1: Int ->
                        if (code1 == 0) {
                            profileViewModel.imageFile = file
                            profileViewModel.editProfileCompleteEvent.call()
                        }
                    }
                } ?: profileViewModel.editProfileCompleteEvent.call()


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
                profileViewModel.noticeOptionLiveData.value = option
            }
        }
    }

    fun onSelectInviteFriendOptionClick(option: Boolean) {
        profileViewModel.profileRepository.updateOption(profileViewModel.noticeOptionLiveData.value!!, option, profileViewModel.invitePlanOptionLiveData.value!!) { code: Int ->
            if(code == 0) {
                profileViewModel.inviteFriendOptionLiveData.value = option
            }
        }
    }

    fun onSelectInvitePlanOptionClick(option: Boolean) {
        profileViewModel.profileRepository.updateOption(profileViewModel.noticeOptionLiveData.value!!, profileViewModel.inviteFriendOptionLiveData.value!!, option) { code: Int ->
            if(code == 0) {
                profileViewModel.invitePlanOptionLiveData.value = option
            }
        }
    }

    fun onLogoutClick() {
        profileViewModel.profileRepository.logout { code: Int ->
            if (code == 0) {
                val account = profileViewModel.profileRepository.getSavedAccount()
                val auth = FirebaseAuth.getInstance()
                when (account!!.flag) {
                    1 -> {
                        auth.signOut()
                        profileViewModel.googleSignInClient.signOut()
                            .addOnCompleteListener(OnCompleteListener {
                            Log.d("TAG", "TEST")
                        })
                    }
                    2 -> {
                        profileViewModel.naverLogoutEvent.call()
                    }
                }
                deleteUserInfo()
                profileViewModel.profileRepository.deleteAccount()
                profileViewModel.logoutEvent.call()
            }
        }
    }

    private fun deleteUserInfo() {
        UserInfo.isLogin = false
        UserInfo.userId = ""
    }
}
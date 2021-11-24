package org.sehproject.sss.logic

import android.util.Log
import android.widget.CompoundButton
import org.sehproject.sss.datatype.Profile
import org.sehproject.sss.viewmodel.ProfileViewModel

class ProfileLogic(val profileViewModel: ProfileViewModel) {
    val profileRepository = profileViewModel.profileRepository

    fun onEditProfileClick(profile: Profile) {
        profileViewModel.editProfileEvent.value = profile
        profileViewModel.editProfileEvent.call()
    }

    fun onEditProfileCompleteClick(profile: Profile) {
        profileRepository.editProfile(profile) { code: Int ->
            if (code == 0) {
                profileViewModel.editProfileCompleteEvent.call()
            }
        }
    }

    fun onUploadImageClick() {}

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
        Log.d("TAG", "option: $option")
    }

    fun onSelectInviteFriendOptionClick(option: Boolean) {
        Log.d("TAG", "option: $option")
    }

    fun onSelectInvitePlanOptionClick(option: Boolean) {
        Log.d("TAG", "option: $option")
    }

    fun onLogoutClick() {
        profileRepository.logout { code: Int ->
            if (code == 0) {
                val account = profileRepository.getSavedAccount()
                when(account!!.flag) {
                    // 1 ->
                    // 2 ->
                }
                profileRepository.deleteAccount()
                profileViewModel.logoutEvent.call()
            }
        }
        profileViewModel.logoutEvent.call()
    }
}
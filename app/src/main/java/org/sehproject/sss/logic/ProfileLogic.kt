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
            if(code == 0) {
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
        profileViewModel.logoutEvent.call()

//        profileRepository.logout() { code: Int ->
//            if(code == 0) {
//                profileRepository.deleteAccount()
//                profileViewModel.logoutEvent.call()
//            }
//        }
    }
}
package org.sehproject.sss.logic

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

    fun onSelectNoticeOptionClick(option: Boolean) {}
    fun onSelectInviteFriendOptionClick(option: Boolean) {}
    fun onSelectInvitePlanOptionClick(option: Boolean) {}

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
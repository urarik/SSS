package org.sehproject.sss.logic

import org.sehproject.sss.viewmodel.ProfileViewModel

class ProfileLogic(val profileViewModel: ProfileViewModel) {
    fun onEditProfileClick() {
        profileViewModel.editProfileEvent.call()
    }
    fun onEditProfileCompleteClick() {
        profileViewModel.editProfileCompleteEvent.call()
    }
    fun onUploadImageClick() {}
    fun onViewStatisticsClick() {}
    fun onViewStatisticsExitClick() {}
    fun onSelectOptionClick() {
        profileViewModel.selectOptionEvent.call()
    }
    fun onSelectNoticeOptionClick(option: Boolean) {}
    fun onSelectInviteFriendOptionClick(option: Boolean) {}
    fun onSelectInvitePlanOptionClick(option: Boolean) {}
    fun onLogoutClick() {}
}
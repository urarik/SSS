package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.datatype.*
import org.sehproject.sss.logic.PlanLogic
import org.sehproject.sss.logic.ProfileLogic
import org.sehproject.sss.repository.ProfileRepository
import org.sehproject.sss.repository.UserRepository
import org.sehproject.sss.utils.SingleLiveEvent

class ProfileViewModel(appDatabase: AppDatabase): ViewModel() {
    val profileLogic = ProfileLogic(this)
    val profileRepository = ProfileRepository(appDatabase);

    val viewProfileEvent = SingleLiveEvent<Int>()
    val editProfileEvent = SingleLiveEvent<Profile>()
    val editProfileCompleteEvent = SingleLiveEvent<Int>()
    val uploadImageEvent = SingleLiveEvent<Any>()
    val viewStatisticsEvent = SingleLiveEvent<Int>()
    val viewStatisticsExitEvent = SingleLiveEvent<Any>()
    val viewFriendProfileEvent = SingleLiveEvent<Int>()
    val selectOptionEvent = SingleLiveEvent<Int>()
    val logoutEvent = SingleLiveEvent<Int>()
    var profileLiveData = MutableLiveData<Profile>()
    var statisticsLiveData = MutableLiveData<Statistics>()
    var planListLiveData = MutableLiveData<List<SimplePlan>>()

    fun setProfile(userId: String) {
        profileRepository.getProfile(userId) { code: Int, profile: Profile? ->
            if(code == 0) {
                profileLiveData.value = profile
            }
        }
    }
    fun setStatistics() {

    }


}
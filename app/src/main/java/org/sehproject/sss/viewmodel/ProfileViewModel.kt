package org.sehproject.sss.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.datatype.*
import org.sehproject.sss.logic.PlanLogic
import org.sehproject.sss.logic.ProfileLogic
import org.sehproject.sss.utils.SingleLiveEvent

class ProfileViewModel: ViewModel() {
    val profileLogic = ProfileLogic(this)

    val viewProfileEvent = SingleLiveEvent<Int>()
    val editProfileEvent = SingleLiveEvent<Profile>()
    val editProfileCompleteEvent = SingleLiveEvent<Int>()
    val uploadImageEvent = SingleLiveEvent<Any>()
    val viewStatisticsEvent = SingleLiveEvent<Int>()
    val viewStatisticsExitEvent = SingleLiveEvent<Any>()
    val viewFriendProfileEvent = SingleLiveEvent<Int>()
    val selectOptionEvent = SingleLiveEvent<Int>()
    val logoutEvent = SingleLiveEvent<Int>()
    val profileLiveData = MutableLiveData<Profile>()
    val statisticsLiveData = MutableLiveData<Statistics>()
    val planListLiveData = MutableLiveData<List<SimplePlan>>()

    fun setProfile(userId: String) {

    }
    fun setStatistics() {}


}
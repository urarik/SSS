package org.sehproject.sss.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.datatype.*
import org.sehproject.sss.logic.PlanLogic
import org.sehproject.sss.logic.ProfileLogic
import org.sehproject.sss.repository.ProfileRepository
import org.sehproject.sss.repository.UserRepository
import org.sehproject.sss.utils.SingleLiveEvent
import java.io.File

class ProfileViewModel(appDatabase: AppDatabase) : ViewModel() {
    val profileLogic = ProfileLogic(this)
    val profileRepository = ProfileRepository(appDatabase);

    val viewProfileEvent = SingleLiveEvent<Int>()
    val viewFriendProfileEvent = SingleLiveEvent<Int>()

    // profile -> userId
    val editProfileEvent = SingleLiveEvent<String>()
    val editProfileCompleteEvent = SingleLiveEvent<Int>()
    val uploadImageEvent = SingleLiveEvent<Any>()
    val viewStatisticsEvent = SingleLiveEvent<Int>()
    val viewStatisticsExitEvent = SingleLiveEvent<Any>()
    val selectOptionEvent = SingleLiveEvent<Int>()
    val logoutEvent = SingleLiveEvent<Int>()
    var profileLiveData = MutableLiveData<Profile>()
    var statisticsLiveData = MutableLiveData<Statistics>()
    var planListLiveData = MutableLiveData<List<SimplePlan>>()

    // option Live Data 추가
    var optionLiveData = MutableLiveData<Option>()
    var noticeOptionLiveData = MutableLiveData(false)
    var inviteFriendOptionLiveData = MutableLiveData(false)
    var invitePlanOptionLiveData = MutableLiveData(false)
    var imageFile = File("/storage/emulated/0/Download/17cced4abfa242da.jpg")
    var imageBitmapLiveData = MutableLiveData<Bitmap>()
    var imageUri: Uri? = null

    fun setProfile(userId: String) {
        profileRepository.getProfile(userId) { code: Int, profile: Profile? ->
            if (code == 0) {
                profileLiveData.value = profile
            }
        }
        profileRepository.getProfileImage(userId) { code: Int, bitmap: Bitmap? ->
            if (code == 0) {
                imageBitmapLiveData.value = bitmap
            }
        }
    }

    fun setStatistics() {
        profileRepository.getStatistics { code: Int, statistics: Statistics? ->
            if(code == 0) {
                statisticsLiveData.value = statistics
            }
        }
    }

    fun setOption() {
        profileRepository.getOption() { code: Int, option: Option? ->
            if(code == 0) {
                optionLiveData.value = option
            }
        }
    }
}
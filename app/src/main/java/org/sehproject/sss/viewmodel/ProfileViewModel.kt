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
//        profileLiveData.value = Profile("764545", "아무개", "정", 24, false)
//        imageBitmapLiveData.value = BitmapFactory.decodeFile(imageFile.absolutePath)

        profileRepository.getProfile(userId) { code: Int, profile: Profile? ->
            if (code == 0) {
                profileRepository.getProfileImage(userId) { code: Int, bitmap: Bitmap? ->
                    if (code == 0) {
                        profileLiveData.value = profile
                        imageBitmapLiveData.value = bitmap
                    }
                }
            }
        }
    }

    fun setStatistics() {
        //statisticsLiveData.value = Statistics()

//        profileRepository.getStatistics() { code: Int, statistics: Statistics? ->
//            if(code == 0) {
//                statisticsLiveData.value = statistics
//            }
//        }
    }

    fun setOption() {
        optionLiveData.value = Option(true, true, false)

//        profileRepository.getOption() { code: Int, option: Option? ->
//            if(code == 0) {
//                optionLiveData.value = option
//            }
//        }
    }
}
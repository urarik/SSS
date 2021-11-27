package org.sehproject.sss.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.datatype.*
import org.sehproject.sss.logic.PlanLogic
import org.sehproject.sss.logic.ProfileLogic
import org.sehproject.sss.repository.ProfileRepository
import org.sehproject.sss.repository.UserRepository
import org.sehproject.sss.utils.SingleLiveEvent
import java.io.File
import java.io.FileInputStream

class ProfileViewModel(appDatabase: AppDatabase): ViewModel() {
    val profileLogic = ProfileLogic(this)
    val profileRepository = ProfileRepository(appDatabase);

    val viewProfileEvent = SingleLiveEvent<Int>()
    val viewFriendProfileEvent = SingleLiveEvent<Int>()
    var planListLiveData = MutableLiveData<List<SimplePlan>>()

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
    val naverLogoutEvent = SingleLiveEvent<Any>()

    // option Live Data 추가
    var optionLiveData = MutableLiveData<Option>()
    var noticeOptionLiveData = MutableLiveData(false)
    var inviteFriendOptionLiveData = MutableLiveData(false)
    var invitePlanOptionLiveData = MutableLiveData(false)
    var imageBitmapLiveData = MutableLiveData<Bitmap>()
    var imageFile: File? = null
    var imageUri: Uri? = null
    var imageStream: FileInputStream? = null
    var imageLength: Int = 0
    var imageExtension = ""

    // 통계용 변수 추가
    val getStatisticsCompleteEvent = SingleLiveEvent<Any>()
    var abscentPlanCountLiveData = MutableLiveData<Int>()
    var attendPercentLiveData = MutableLiveData<Double>()
    var isTotalPlanCount: Boolean = false
    var isAttendPlanCount: Boolean = false
    var isAbscentPlanCount: Boolean = false
    var isAttendPercent: Boolean = false
    lateinit var googleSignInClient: GoogleSignInClient


    fun setProfile(userId: String) {
        profileRepository.getProfile(userId) { code: Int, profile: Profile? ->
            if(code == 0) {
                profileLiveData.value = profile
            }
        }
        profileRepository.getProfileImage(userId) { code: Int, bitmap: Bitmap? ->
            if(code == 0) {
                imageBitmapLiveData.value = bitmap
            }
        }
    }
    fun setStatistics() {
        profileRepository.getStatistics() { code: Int, statistics: Statistics? ->
            if(code == 0) {
                statisticsLiveData.value = statistics
                abscentPlanCountLiveData.value = statisticsLiveData.value!!.totalPlanCount - statisticsLiveData.value!!.attendPlanCount
                attendPercentLiveData.value = statisticsLiveData.value!!.attendPlanCount.toDouble() / statisticsLiveData.value!!.totalPlanCount.toDouble()
                if (statisticsLiveData.value!!.point >= 100) { isTotalPlanCount = true }
                if (statisticsLiveData.value!!.point >= 200) { isAttendPlanCount = true }
                if (statisticsLiveData.value!!.point >= 300) { isAbscentPlanCount = true }
                if (statisticsLiveData.value!!.point >= 400) { isAttendPercent = true }
                getStatisticsCompleteEvent.call()
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
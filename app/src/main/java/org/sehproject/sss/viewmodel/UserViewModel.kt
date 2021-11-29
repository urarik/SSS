package org.sehproject.sss.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.logic.UserLogic
import org.sehproject.sss.repository.UserRepository
import org.sehproject.sss.utils.ActivityNavigation
import org.sehproject.sss.utils.LiveMessageEvent
import org.sehproject.sss.utils.SingleLiveEvent

class UserViewModel(appDatabase: AppDatabase) : ViewModel() {
    val userLogic = UserLogic(this)
    val userRepository = UserRepository(appDatabase)

    val loginEvent = SingleLiveEvent<Any>()
    val registerEvent = SingleLiveEvent<Any>()
    val registerApiEvent = SingleLiveEvent<String>()
    val registerCompleteEvent = SingleLiveEvent<Any>()
    val loginFailEvent = SingleLiveEvent<String>()

    val googleLoginEvent = LiveMessageEvent<ActivityNavigation>()
    val googleRegisterEvent = LiveMessageEvent<ActivityNavigation>()
    val registerFailEvent = SingleLiveEvent<Any>()
    lateinit var googleSignInClient: GoogleSignInClient
    var token = ""

    fun setGoogleClient(_googleSignInClient: GoogleSignInClient) {
        googleSignInClient = _googleSignInClient
    }
}
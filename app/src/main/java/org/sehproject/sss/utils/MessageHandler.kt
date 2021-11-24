package org.sehproject.sss.utils

import android.content.BroadcastReceiver
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.sehproject.sss.viewmodel.UserViewModel

class MessageHandler: FirebaseMessagingService() {
    private lateinit var localBroadcastManager: LocalBroadcastManager

    override fun onCreate() {
        super.onCreate()
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.data.isEmpty()) {
            return
        } else {
            val intent = Intent("Invitation")
            intent.putExtra("target_name", remoteMessage.data["target_name"])
            intent.putExtra("invite_type", remoteMessage.data["invite_type"])
            intent.putExtra("inviter", remoteMessage.data["inviter"])

            localBroadcastManager.sendBroadcast(intent)
        }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    companion object {

    }
}
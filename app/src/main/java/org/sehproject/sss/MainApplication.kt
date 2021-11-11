package org.sehproject.sss

import android.app.Application
import org.sehproject.sss.repository.LoginRepository

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        LoginRepository.initialize(this)
    }
}
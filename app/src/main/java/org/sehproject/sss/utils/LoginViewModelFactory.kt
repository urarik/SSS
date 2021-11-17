package org.sehproject.sss.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.viewmodel.UserViewModel

class UserViewModelFactory(private val appDatabase: AppDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(appDatabase) as T
    }

}
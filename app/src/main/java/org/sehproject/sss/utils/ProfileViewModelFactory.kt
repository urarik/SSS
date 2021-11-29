package org.sehproject.sss.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.sehproject.sss.dao.AppDatabase
import org.sehproject.sss.viewmodel.ProfileViewModel

class ProfileViewModelFactory(private val appDatabase: AppDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(appDatabase) as T
    }
}
package org.sehproject.sss.utils

import retrofit2.Call
import retrofit2.Callback

abstract class CallbackWithRetry<T>(private val call: Call<T>) : Callback<T> {
    private var retryCount = 0
    fun onFailure(onFail: () -> Unit) {
        if (retryCount++ < TOTAL_RETRIES) {
            retry()
        }
        onFail()
    }

    private fun retry() {
        call.clone().enqueue(this)
    }

    companion object {
        private const val TOTAL_RETRIES = 3
    }
}
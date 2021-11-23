package org.sehproject.sss.utils

import android.os.AsyncTask
import android.util.Log
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import java.io.IOException

class CreateEventTask(private val mService: Calendar, private val event: Event):
    CoroutinesAsyncTask<Void, Void, Unit>("Google calendar add event") {
    override fun doInBackground(vararg params: Void?): Unit {
        try{
            val calendarId = "primary"
            Log.d("TAG", event.toString())
            val result = mService.events().insert(calendarId, event).execute()
            Log.d("TAG", mService.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
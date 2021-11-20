package org.sehproject.sss.datatype

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.sehproject.sss.UserInfo


@Parcelize
data class Plan(
    var pid: Int? = null,
    var name: String = "",
    var date: String = "",
    var startTime: String = "",
    var endTime: String = "",
    var location: String = "",
    var category: String = "",
    var creator: String? = UserInfo.userId,
    var participants: List<User>? = null,
    var sync: Boolean = false): Parcelable {
  init {
      participants = mutableListOf()
              }}

package org.sehproject.sss.datatype

import org.sehproject.sss.UserInfo


data class Plan(
    var pid: Int? = -1,
    var name: String,
    var date: String = "",
    var startTime: String,
    var endTime: String,
    var location: String,
    var category: String,
    var creator: String? = UserInfo.userId,
    var participants: List<User>? = null,
    var sync: Boolean = false) {
  init {
      participants = mutableListOf()
              }}

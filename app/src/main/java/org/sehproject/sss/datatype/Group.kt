package org.sehproject.sss.datatype

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    var gid: Int?,
    var name: String,
    var explanation: String,
    var color: Int,
    var creator: String?,
    var participants: List<User>?
)
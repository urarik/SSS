package org.sehproject.sss.datatype

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    var gid: Int? = null,
    var name: String = "",
    var explanation: String = "",
    var color: String = "",
    var creator: String = "",
    var participants: List<User>? = null
): Parcelable

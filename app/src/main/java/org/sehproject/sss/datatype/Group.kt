package org.sehproject.sss.datatype

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.sehproject.sss.UserInfo

@Parcelize
data class Group(
    var gid: Int? = null,
    var name: String = "",
    var explanation: String = "",
    var color: Int = 0,
    var creator: String = UserInfo.userId,
    var participants: List<User>? = null
): Parcelable
package org.sehproject.sss.datatype

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var userId: String = "",
    var nickName: String = "",
    var isOnline: Boolean = true,
    var isAttend: Boolean = true
): Parcelable

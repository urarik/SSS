package org.sehproject.sss.datatype

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Invitation(val id: Int, val type: String, val inviter: String, val name: String): Parcelable {
    override fun toString(): String {
        return "$inviter 님이 $type ${name}에 초대했습니다.\n 수락하시겠습니까?"
    }
}

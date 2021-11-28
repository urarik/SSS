package org.sehproject.sss.datatype

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(var userId: String="",
                   var nickName: String="",
                   var name: String="",
                   var age: String="",
                   var gender: Boolean=true, //true면 남자
                   // var image: ByteArray = ByteArray(1)
        ): Parcelable
package org.sehproject.sss.datatype

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(var userId: String,
                   var nickName: String,
                   var name: String,
                   var age: Int,
                   var gender: Boolean,
                   var image: String): Parcelable

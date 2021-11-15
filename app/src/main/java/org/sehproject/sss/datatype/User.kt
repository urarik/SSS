package org.sehproject.sss.datatype

data class User(
    var userId: String,
    var nickName: String,
    var isOnline: Boolean,
    var isAttend: Boolean
)

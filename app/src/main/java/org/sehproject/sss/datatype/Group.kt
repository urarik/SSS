package org.sehproject.sss.datatype

data class Group(
    var gid: Int?,
    var name: String,
    var explanation: String,
    var color: String,
    var creator: String?,
    var participants: List<User>?
)

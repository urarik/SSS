package org.sehproject.sss.datatype

data class Friend(var userId: String = "",
                  var nickName: String = "",
                  var isOnline: Boolean = false,
                  var isAttend: Boolean = false)

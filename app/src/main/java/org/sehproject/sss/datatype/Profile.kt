package org.sehproject.sss.datatype

data class Profile(var userId: String,
                   var nickName: String,
                   var name: String,
                   var age: Int,
                   var gender: Boolean,
                   var image: ByteArray)

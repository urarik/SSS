package org.sehproject.sss.datatype

import androidx.room.ColumnInfo

data class AccountXML(
    var userId: String,
    var password: String,
    var apiId: String,
    var flag: Int
)

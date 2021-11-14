package org.sehproject.sss.datatype

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey
    @ColumnInfo
    val userId: String,

    @ColumnInfo
    val password: String
)

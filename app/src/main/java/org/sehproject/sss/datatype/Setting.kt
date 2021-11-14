package org.sehproject.sss.datatype

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Setting(
    @PrimaryKey
    @ColumnInfo
    val userId: String,

    @ColumnInfo
    val notification: Boolean,

    @ColumnInfo
    val friendInvitation: Boolean,

    @ColumnInfo
    val planInvitation: Boolean
)

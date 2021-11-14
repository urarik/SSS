package org.sehproject.sss.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.sehproject.sss.datatype.Setting

@Dao
interface SettingDao {
    @Query("select * from setting where :userId")
    fun select(userId: String): Setting?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(setting: Setting)

    @Query("delete from setting")
    fun delete()
}
package org.sehproject.sss.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.sehproject.sss.datatype.Account

@Dao
interface UserDao {

    @Query("select * from account")
    fun select(): Account?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: Account)

    @Query("delete from account")
    fun delete()
}
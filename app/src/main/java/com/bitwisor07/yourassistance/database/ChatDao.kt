package com.bitwisor07.yourassistance.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bitwisor07.yourassistance.Data.ChatEntity

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chatEntity: ChatEntity)

    @Query("DELETE FROM chats")
    fun deletall()

    @Query("SELECT * FROM chats")
    fun getChatDao(): LiveData<List<ChatEntity>>

}
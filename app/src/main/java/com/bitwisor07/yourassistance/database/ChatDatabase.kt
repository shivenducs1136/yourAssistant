package com.bitwisor07.yourassistance.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bitwisor07.yourassistance.Data.ChatEntity


@Database(entities = [ChatEntity::class],version = 1)
abstract class ChatDatabase: RoomDatabase() {

    abstract fun chatDao(): ChatDao
    companion object{

        @Volatile
        private var INSTANCE: ChatDatabase? = null
        fun getDatabase(context: Context): ChatDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "chat_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }

    }
}
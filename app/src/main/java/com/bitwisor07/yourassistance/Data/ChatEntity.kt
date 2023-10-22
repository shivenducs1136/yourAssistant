package com.bitwisor07.yourassistance.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val robotmsg : String,
    val humanmsg : String,
    val isHuman:Boolean
)
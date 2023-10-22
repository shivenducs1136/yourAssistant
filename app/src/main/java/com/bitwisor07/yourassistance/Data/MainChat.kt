package com.bitwisor07.yourassistance.Data

data class MainChat(
    val choices: List<Choice?>? = null,
    val created: Int? = null,
    val id: String? = null,
    val model: String? = null,
    val `object`: String? = null,
    val usage: Usage? = null
)
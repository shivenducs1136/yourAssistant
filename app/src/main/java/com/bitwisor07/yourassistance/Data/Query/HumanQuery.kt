package com.bitwisor07.yourassistance.Data.Query

data class HumanQuery(
    val frequency_penalty: Int? = null,
    val max_tokens: Int? = null,
    val model: String? = null,
    val presence_penalty: Double? = null,
    val prompt: String? = null,
    val stop: List<String?>? = null,
    val temperature: Int? = null,
    val top_p: Int? = null
)
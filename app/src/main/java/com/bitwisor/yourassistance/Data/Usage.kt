package com.bitwisor.yourassistance.Data

data class Usage(
    val completion_tokens: Int? = null,
    val prompt_tokens: Int? = null,
    val total_tokens: Int? = null
)
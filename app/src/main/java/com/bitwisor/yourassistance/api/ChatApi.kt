package com.bitwisor.yourassistance.api

import com.bitwisor.yourassistance.Data.MainChat
import com.bitwisor.yourassistance.Data.Query.HumanQuery
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatApi {
    @POST("v1/completions")
    suspend fun postQuery(
        @Body
        humanQuery: HumanQuery
    ): Response<MainChat>
}
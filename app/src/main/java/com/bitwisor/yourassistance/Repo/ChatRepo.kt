package com.bitwisor.yourassistance.Repo

import com.bitwisor.yourassistance.Data.ChatEntity
import com.bitwisor.yourassistance.Data.MainChat
import com.bitwisor.yourassistance.Data.Query.HumanQuery
import com.bitwisor.yourassistance.api.RetrofitInstance
import retrofit2.Response

class ChatRepo() {
    suspend fun getRespond(humanQuery: HumanQuery) : Response<MainChat> {
        return RetrofitInstance.api.postQuery(humanQuery)
    }

}
package com.bitwisor07.yourassistance.Repo

import com.bitwisor07.yourassistance.Data.MainChat
import com.bitwisor07.yourassistance.Data.Query.HumanQuery
import com.bitwisor07.yourassistance.api.RetrofitInstance
import retrofit2.Response

class ChatRepo() {
    suspend fun getRespond(humanQuery: HumanQuery) : Response<MainChat> {
        return RetrofitInstance.api.postQuery(humanQuery)
    }

}
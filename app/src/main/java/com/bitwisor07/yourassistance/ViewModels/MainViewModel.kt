package com.bitwisor07.yourassistance.ViewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitwisor07.yourassistance.Data.ChatEntity
import com.bitwisor07.yourassistance.Data.MainChat
import com.bitwisor07.yourassistance.Data.Query.HumanQuery
import com.bitwisor07.yourassistance.Repo.ChatRepo
import com.bitwisor07.yourassistance.database.ChatDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.SocketTimeoutException

class MainViewModel(val chatrepo:ChatRepo,val context: Context):ViewModel() {
    val botchat: MutableLiveData<Resource<ChatEntity>?> = MutableLiveData()
    private val dao = ChatDatabase.getDatabase(context).chatDao()
    val detailsofChat = dao.getChatDao()


    fun getResp(humanQuery: HumanQuery) =
        viewModelScope.launch {
            botchat.postValue(Resource.Loading())
            var response:Response<MainChat>
            try{
                 response = chatrepo.getRespond(humanQuery)
                botchat.postValue(handleBreakingNewsResponse(response))
            }catch (e:SocketTimeoutException){
                botchat.postValue(null)
            }
    }
    fun insertIntoDb(s:ChatEntity) = viewModelScope.launch (Dispatchers.IO){
            dao.insert(s)
    }
    fun deleteAllChats() = viewModelScope.launch (Dispatchers.IO){
        dao.deletall()
    }


    private fun handleBreakingNewsResponse(response: Response<MainChat>) : Resource<ChatEntity> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                var robotmsg = "Sorry I didn't understand you."
                var re = resultResponse
                if(re.choices?.get(0) !=null){
                    val str = re.choices!![0]!!.text
                    if(str!=null){
                        robotmsg = refineString(str)
                    }
                }
                val chten = ChatEntity(0,robotmsg,"",false)
                insertIntoDb(chten)
                return Resource.Success(chten)
            }
        }
        return Resource.Error(response.message())
    }

    private fun refineString(str: String): String {
        val trimmedString: String = str.trim()
        return trimmedString
    }


}
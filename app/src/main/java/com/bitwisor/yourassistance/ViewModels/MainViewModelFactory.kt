package com.bitwisor.yourassistance.ViewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bitwisor.yourassistance.Repo.ChatRepo

class MainViewModelFactory(
    val chatrepo: ChatRepo,
    val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(chatrepo, context) as T
    }
}
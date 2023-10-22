package com.bitwisor07.yourassistance

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bitwisor.yourassistance.R
import com.bitwisor07.yourassistance.Repo.ChatRepo
import com.bitwisor07.yourassistance.ViewModels.MainViewModel
import com.bitwisor07.yourassistance.ViewModels.MainViewModelFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       try{
           val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
           val configSettings = remoteConfigSettings {
               minimumFetchIntervalInSeconds = 3600
           }
           remoteConfig.setConfigSettingsAsync(configSettings)
           remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
           remoteConfig.fetchAndActivate()
               .addOnCompleteListener(this) { task ->
                   if (task.isSuccessful) {
                       val updated = task.result
                       Log.d(ContentValues.TAG, "Config params updated: $updated")

                   } else {
                       Toast.makeText(this,"API key update failed",Toast.LENGTH_SHORT).show()

                   }

               }
       }
       catch (e:Exception){
           Toast.makeText(this,"No internet to check for Updates",Toast.LENGTH_SHORT).show()
       }
        val charepo = ChatRepo()
        val viewModelProviderFactory = MainViewModelFactory(charepo,this)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)
    }
}
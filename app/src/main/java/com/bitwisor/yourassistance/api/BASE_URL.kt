package com.bitwisor.yourassistance.api

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object BASE_URL {
    var BASE_URL = "https://api.openai.com/"
    val remoteConfig = Firebase.remoteConfig
    val welcomeMessage = remoteConfig["token"].asString()
    var token = welcomeMessage
}

package com.bitwisor.yourassistance.api

import com.bitwisor.yourassistance.Data.MainChat
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {


    companion object {

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging).addInterceptor {
                    val req = it.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${BASE_URL.token}").build()
                    it.proceed(req)
                }
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api by lazy {
            retrofit.create(ChatApi::class.java)
        }

    }
}


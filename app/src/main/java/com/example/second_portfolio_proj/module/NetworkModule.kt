package com.example.second_portfolio_proj.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


open class NetworkModule {

    fun getRetrofit(BaseUrl:String):Retrofit  {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = (HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }



}
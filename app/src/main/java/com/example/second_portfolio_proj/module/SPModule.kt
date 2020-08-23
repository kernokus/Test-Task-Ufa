package com.example.second_portfolio_proj.module

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class SPModule (_context:Context){
    private var context=_context

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }




}


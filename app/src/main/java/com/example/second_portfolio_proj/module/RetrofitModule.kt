package com.example.second_portfolio_proj.module

import com.example.second_portfolio_proj.module.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class RetrofitModule() {

    @Provides
    @Singleton
    open fun provideNetworkModule(): NetworkModule {
        return NetworkModule()
    }


}
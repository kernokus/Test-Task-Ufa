package com.example.second_portfolio_proj

import android.app.Application
import com.example.second_portfolio_proj.module.RetrofitModule
import com.example.second_portfolio_proj.module.SPModule

class MyApplication:Application() {
    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
          appComponent = createComponent()
    }

     private fun createComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .retrofitModule(RetrofitModule())
            .sPModule(SPModule(this))
            .build()
    }

    fun getComponent(): AppComponent {
        return appComponent
    }
}

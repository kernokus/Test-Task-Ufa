package com.example.second_portfolio_proj

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.example.second_portfolio_proj.module.RetrofitModule
import com.example.second_portfolio_proj.module.RoomModule
import com.example.second_portfolio_proj.module.SPModule


class MyApplication:Application() {
    private val networkReceiver=NetworkReceiver()
    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
          appComponent = createComponent()

        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
        this.registerReceiver(networkReceiver,intentFilter)
    }

     private fun createComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .retrofitModule(RetrofitModule())
            .sPModule(SPModule(this))
            .roomModule(RoomModule(this))
            .build()
    }

    fun getComponent(): AppComponent {
        return appComponent
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(networkReceiver)
    }
}

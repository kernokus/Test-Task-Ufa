package com.example.second_portfolio_proj


import com.example.second_portfolio_proj.activities.NasaActivity
import com.example.second_portfolio_proj.activities.RegLogActivity
import com.example.second_portfolio_proj.activities.WebViewActivity
import com.example.second_portfolio_proj.module.RetrofitModule
import com.example.second_portfolio_proj.module.SPModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, SPModule::class])
interface AppComponent {
    fun inject(nasaActivity: NasaActivity)
    fun inject(webViewActivity: WebViewActivity)
    fun inject(regLogActivity: RegLogActivity)

}
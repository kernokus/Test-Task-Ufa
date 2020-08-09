package com.example.second_portfolio_proj


import com.example.second_portfolio_proj.activities.MainActivity
import com.example.second_portfolio_proj.activities.RegLogActivity
import com.example.second_portfolio_proj.activities.RegistrationActivity
import com.example.second_portfolio_proj.activities.WebViewActivity
import com.example.second_portfolio_proj.module.RetrofitModule
import com.example.second_portfolio_proj.module.RoomModule
import com.example.second_portfolio_proj.module.SPModule
import com.example.second_portfolio_proj.presenters.RegLogActivityPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, SPModule::class,RoomModule::class])
interface AppComponent {
    fun inject(nasaActivity: MainActivity)
    fun inject(webViewActivity: WebViewActivity)
    fun inject(regLogActivity: RegLogActivity)
    fun inject(registrationActivity: RegistrationActivity)
    fun inject(regLogActivityPresenter: RegLogActivityPresenter)




}
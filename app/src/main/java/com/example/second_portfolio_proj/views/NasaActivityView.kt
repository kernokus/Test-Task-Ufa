package com.example.second_portfolio_proj.views


import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = OneExecutionStateStrategy::class) //разобраться
interface NasaActivityView :MvpView{
    fun addInView(message:String)
    fun goInWebView(url:String)
    fun goReg()


}
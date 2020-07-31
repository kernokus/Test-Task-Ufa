package com.example.second_portfolio_proj.presenters

import android.content.SharedPreferences
import com.example.second_portfolio_proj.views.NasaActivityView
import com.example.second_portfolio_proj.views.RegActivityView
import moxy.MvpPresenter
import moxy.presenter.InjectPresenter

class RegActivityPresenter: MvpPresenter<RegActivityView>() {
    companion object {
        const val PASSWORD="password"
        const val LOGIN="login"
        const val IS_CHECK_BOX="is check box"


    }

    fun saveLogAndPassw(sp:SharedPreferences,login:String,password:String,isCheckBox:Boolean) {
        sp.edit().putString(LOGIN,login).putString(PASSWORD,password).putBoolean(IS_CHECK_BOX,isCheckBox).apply()
    }

    fun isCheckBox(sp:SharedPreferences):Boolean{
        if (sp.getBoolean(IS_CHECK_BOX,false)) return true
        return false
    }

    fun loadInfo(sp: SharedPreferences) {

    }

    fun removeInfo(sp: SharedPreferences) {
        sp.edit().remove(PASSWORD).remove(LOGIN).remove(IS_CHECK_BOX).apply()
    }


}
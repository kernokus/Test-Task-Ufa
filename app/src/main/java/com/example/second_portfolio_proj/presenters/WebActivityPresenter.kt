package com.example.second_portfolio_proj.presenters

import android.annotation.TargetApi
import android.content.SharedPreferences

import android.os.Build
import android.webkit.*
import androidx.annotation.RequiresApi
import com.example.second_portfolio_proj.views.WebActivityView
import moxy.MvpPresenter


class WebActivityPresenter: MvpPresenter<WebActivityView>() {





    class MyWeb(): WebViewClient() {


        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return true
        }
    }
    fun saveState(sp:SharedPreferences,url:String){
    sp.edit().putString(MainActivityPresenter.IS_NOT_FIRST,url).apply()
    }



}
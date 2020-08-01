package com.example.second_portfolio_proj.presenters

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.second_portfolio_proj.*
import com.example.second_portfolio_proj.API.RequestPhpService
import com.example.second_portfolio_proj.module.NetworkModule
import com.example.second_portfolio_proj.views.NasaActivityView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NasaActivityPresenter:MvpPresenter<NasaActivityView>() {
    companion object {
        const val IS_NOT_FIRST="is_not_a_first"
        const val REG="reg"
        const val BaseURL="http://platinum-kaz.ru/"
    }

    fun goRetrofit(network: NetworkModule, sp: SharedPreferences) {
        CoroutineScope(Dispatchers.IO).launch { //TODO переделать
            val service = network.getRetrofit(BaseURL).create(
                RequestPhpService::class.java
            )
            val call = service.getScriptInfo()
            call.enqueue(object : Callback<ScriptPOJO> {
                override fun onResponse(call: Call<ScriptPOJO>, response: Response<ScriptPOJO>) {
                    val phpResponse: ScriptPOJO? = response.body()
                    if (phpResponse != null) {
                        viewState.addInView(phpResponse.asdf + phpResponse.qqqq)

                        phpResponse.url=null
                        if (phpResponse.url!=null) {
                            sp.edit().putString(IS_NOT_FIRST,phpResponse.url).apply()
                            viewState.goInWebView(phpResponse.url.toString())
                        }
                        else {
                            sp.edit().putString(IS_NOT_FIRST, REG).apply()
                            viewState.goReg()
                        }
                    }
                }

                override fun onFailure(call: Call<ScriptPOJO>, t: Throwable) {

                }
            })
        }



    }



    fun isFirst (sp:SharedPreferences) :Boolean {
        if (sp.contains(IS_NOT_FIRST)) return false
        return true
    }

    fun redirect(sp:SharedPreferences) {
        val url=sp.getString(IS_NOT_FIRST,"")
        if (url!= REG && url!=null) {
                viewState.goInWebView(url)
        }
        else viewState.goReg()
    }

//    fun showExitDialog(ctx:Context) {
//        val builder = AlertDialog.Builder(app)
//
//    }


}
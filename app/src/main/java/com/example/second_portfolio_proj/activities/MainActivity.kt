package com.example.second_portfolio_proj.activities



import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog


import com.example.second_portfolio_proj.MyApplication

import com.example.second_portfolio_proj.module.NetworkModule
import com.example.second_portfolio_proj.R
import com.example.second_portfolio_proj.presenters.MainActivityPresenter
import com.example.second_portfolio_proj.views.NasaActivityView
import kotlinx.android.synthetic.main.activity_nasa.*

import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import javax.inject.Inject


class MainActivity:MvpAppCompatActivity(), NasaActivityView {

    @Inject
    lateinit var network: NetworkModule

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    @InjectPresenter
    lateinit var nasaActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nasa)
        (application as MyApplication).getComponent().inject(this)


            if (nasaActivityPresenter.isFirst(sharedPreferences)) {
           //     nasaActivityPresenter.goRetrofit(network, sharedPreferences)
                nasaActivityPresenter.goNetw2(sharedPreferences)
                nasaActivityPresenter.goNetw2(sharedPreferences)
            } else {
                nasaActivityPresenter.redirect(sharedPreferences)
            }

    }

    override fun addInView(message: String) {
        description.text=message
    }

    override fun goInWebView(url: String) { //перенаправляет на WebView
        val intentWeb=Intent(this, WebViewActivity::class.java)
        intentWeb.putExtra("URL",url)
        startActivity(intentWeb)
    }

    override fun goReg() { //перенаправляет на экран регистрации
        val intentReg=Intent(this, RegLogActivity::class.java)
        startActivity(intentReg)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.questionAboutClose)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            super.onBackPressed()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }
        builder.show()
    }


}




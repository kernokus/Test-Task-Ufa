package com.example.second_portfolio_proj.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import com.example.second_portfolio_proj.MyApplication
import com.example.second_portfolio_proj.R
import com.example.second_portfolio_proj.presenters.WebActivityPresenter
import com.example.second_portfolio_proj.views.WebActivityView
import kotlinx.android.synthetic.main.activity_web_view.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import javax.inject.Inject

class WebViewActivity: MvpAppCompatActivity(), WebActivityView {
    companion object{
        const val ROTATION="rotation"
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    @InjectPresenter
    lateinit var webActivityPresenter: WebActivityPresenter

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        (application as MyApplication).getComponent().inject(this)
        webView.webViewClient=WebActivityPresenter.MyWeb()
        webView.settings.javaScriptEnabled=true
        if (savedInstanceState!=null){
            webView.restoreState(savedInstanceState.getBundle(ROTATION))
        } else {
        val temp=intent.getStringExtra("URL")
        webView.loadUrl(temp)
        }
    }
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle:Bundle= Bundle()
        webView.saveState(bundle)
        outState.putBundle(ROTATION,bundle)
    }



    override fun onStop() {
        super.onStop()
        webActivityPresenter.saveState(sharedPreferences,webView.url)
    }


}
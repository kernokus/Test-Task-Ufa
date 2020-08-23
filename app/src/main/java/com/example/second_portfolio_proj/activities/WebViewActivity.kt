package com.example.second_portfolio_proj.activities

import android.R.attr.mimeType
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.webkit.CookieManager
import android.webkit.DownloadListener
import android.webkit.URLUtil
import android.webkit.ValueCallback
import android.widget.Toast
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
        const val REQUEST_SELECT_FILE = 100
        private const val FILECHOOSER_RESULTCODE = 1
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

        webView.settings.loadsImagesAutomatically=true //!


        webView.settings.javaScriptEnabled=true
        if (savedInstanceState!=null){
            webView.restoreState(savedInstanceState.getBundle(ROTATION))
        } else {
        val temp=intent.getStringExtra("URL")
        webView.loadUrl(temp)
        }
        webView.setDownloadListener(DownloadListener{ url: String, userAgent: String, contentDisposition: String, mimeType: String, contentLength: Long ->
            val request = DownloadManager.Request(
                Uri.parse(url)
            )
            request.setMimeType(mimeType)
            val cookies: String = CookieManager.getInstance().getCookie(url)
            request.addRequestHeader("cookie", cookies)
            request.addRequestHeader("User-Agent", userAgent
            )
            request.setDescription("Downloading File...")
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
                    url, contentDisposition, mimeType
                )
            )
            val dm =
                getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(applicationContext, "Downloading File", Toast.LENGTH_LONG).show()

        })
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
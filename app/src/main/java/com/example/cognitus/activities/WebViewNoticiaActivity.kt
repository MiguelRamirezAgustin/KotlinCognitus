package com.example.cognitus.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.cognitus.R

class WebViewNoticiaActivity : AppCompatActivity() {

    var mywebview :WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_noticia)

        mywebview = findViewById<WebView>(R.id.webviewNoticia)


        //actionbar
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.title = "Detalle de Noticia"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }


        val nUrl = intent.getStringExtra("url_noticia")
        mywebview?.webViewClient = object :WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        mywebview?.loadUrl(nUrl)


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

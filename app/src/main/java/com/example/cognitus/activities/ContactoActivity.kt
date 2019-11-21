package com.example.cognitus.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cognitus.R

class ContactoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = "Contacto"
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }
}

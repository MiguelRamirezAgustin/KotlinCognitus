package com.example.cognitus.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cognitus.R

class DetalleNoticiaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_noticia)

        val tituloParma = intent.getStringExtra("tituloNoticia")
        val detalleNoticiaParam = intent.getStringExtra("detalleNoticia")
        val urlImg = intent.getStringExtra("imagenNoticia")

        val tituloDetalle = findViewById<TextView>(R.id.tVTituloDetalle)
        val detalleNoticia = findViewById<TextView>(R.id.tvDetalleNoticia)
        val imgesDetalle = findViewById<ImageView>(R.id.imgNoticiaDetalle)
        val imgVer = findViewById<ImageView>(R.id.imgVer)
        val imgCompartir = findViewById<ImageView>(R.id.imgCompartir)

        //leer img
        val requestManager = Glide.with(this)
        val requestBuilder = requestManager.load(urlImg)
        requestBuilder.into(imgesDetalle)

        tituloDetalle.setText(tituloParma)
        detalleNoticia.setText(detalleNoticiaParam)


        // show backbutton and set custom title on actionbar
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Noticias"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }


        imgCompartir.setOnClickListener{
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type= "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.msj_compartir)+ urlImg)
            startActivity(Intent.createChooser(shareIntent, getString(R.string.titulo_compartir)))
        }

    }
}

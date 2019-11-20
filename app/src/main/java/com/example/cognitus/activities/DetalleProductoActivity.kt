package com.example.cognitus.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cognitus.R

class DetalleProductoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        val titleProductoParamas = intent.getStringExtra("titleProducto")
        val nUrl = intent.getStringExtra("img_url")
        val detalleProductoParmas = intent.getStringExtra("detalle_Producto")
        val detallePrecioParams = intent.getStringExtra("detalle_precio")

        val imgProductoDetalle = findViewById<ImageView>(R.id.imgProductoDetalle)
        val tvTituloProductoDetalle = findViewById<TextView>(R.id.tVProductoDetalleTitulo)
        val tvDetalleProducto = findViewById<TextView>(R.id.tVDetalleProducto)
        val tvDetallePrecio = findViewById<TextView>(R.id.tVProductoDetallePrecio)

        //Leer imagen
        val requestManager = Glide.with(this)
        val requestBuilder = requestManager.load(nUrl)
        requestBuilder.into(imgProductoDetalle)

        tvTituloProductoDetalle.setText(titleProductoParamas)
        tvDetalleProducto.setText(detalleProductoParmas)
        tvDetallePrecio.setText("$ "+detallePrecioParams +" MXN")


        val actionBar = supportActionBar
        if(actionBar !=  null){
            actionBar.title = "Detalle de Producto"
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

    }
}

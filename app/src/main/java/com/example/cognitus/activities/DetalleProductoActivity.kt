package com.example.cognitus.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cognitus.R
import com.example.cognitus.task.ApiGetPostHelper
import com.example.cognitus.utilities.DialogAlerta
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class DetalleProductoActivity : AppCompatActivity() {

     var strId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        val titleProductoParamas = intent.getStringExtra("titleProducto")
        val nUrl = intent.getStringExtra("img_url")
        val detalleProductoParmas = intent.getStringExtra("detalle_Producto")
        val detallePrecioParams = intent.getStringExtra("detalle_precio")
        val idProducto = intent.getStringExtra("id_producto")


        val imgProductoDetalle = findViewById<ImageView>(R.id.imgProductoDetalle)
        val tvTituloProductoDetalle = findViewById<TextView>(R.id.tVProductoDetalleTitulo)
        val tvDetalleProducto = findViewById<TextView>(R.id.tVDetalleProducto)
        val tvDetallePrecio = findViewById<TextView>(R.id.tVProductoDetallePrecio)
        val tvCompras = findViewById<ImageView>(R.id.btnCompras)

        //Leer imagen
        val requestManager = Glide.with(this)
        val requestBuilder = requestManager.load(nUrl)
        requestBuilder.into(imgProductoDetalle)

        tvTituloProductoDetalle.setText(titleProductoParamas)
        tvDetalleProducto.setText(detalleProductoParmas)
        tvDetallePrecio.setText("$ "+detallePrecioParams +" MXN")
        strId = idProducto
        Log.d("Id_Producto", strId)


        val actionBar = supportActionBar
        if(actionBar !=  null){
            actionBar.title = "Detalle de Producto"
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        tvCompras.setOnClickListener{
            getIdProducto().execute()
        }

    }



    //Inner class Async to consume Ws
    @SuppressLint("StaticFieldLeak")
    inner class getIdProducto: AsyncTask<Void, Void, String>() {

        override fun onPreExecute() {
            pBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: Void?): String {
            //Aquí está el ejemplo de la API de Json.
            val sendParams = HashMap<String, String>()
            //Enviar parámetros y valores en la API JSON
            sendParams["valor"] = ""
            //envíe el HttpPostRequest por HttpURLConnection y reciba un resultado en la cadena de retorno
            return ApiGetPostHelper.SendParams(
                getString(R.string.ws_url_productoId) + "&id_producto=" + strId ,
                sendParams
            )
        }

        override fun onPostExecute(results: String?) {
            //progressbar
            pBar.visibility = View.GONE

            if(results != null){
                //Vea la respuesta en Logcat para comprender los resultados de JSON
                Log.i("Resultado------- ", results)
            }
            try {

                val rootJsonObject = JSONObject(results)
                val validoObject = rootJsonObject.getString("valido")
                if (validoObject == "1") {
                    val usuarioObject = rootJsonObject.getString("respuesta")
                    val mItemObject = JSONObject(usuarioObject)

                    val prodId = mItemObject.getString("orden")

                    //DialogAlerta.crearDialogo(this@DetalleProductoActivity, "", "El id de la orden es: "+prodId, "Alerta",0)
                }else{
                    Toast.makeText(applicationContext,
                        "Datos incorrectos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }catch (e: JSONException){
                Toast.makeText(applicationContext,
                    "Lo sentimos, algo salio mal, ¡Intenta de nuevo!",
                    Toast.LENGTH_SHORT
                ).show()
                e.printStackTrace()
            }
        }
    }



}

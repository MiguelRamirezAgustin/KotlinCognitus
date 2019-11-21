package com.example.cognitus.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cognitus.R
import com.example.cognitus.task.ApiGetPostHelper
import com.example.cognitus.utilities.DialogAlerta
import com.example.cognitus.utilities.Utils
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class ContactoActivity : AppCompatActivity() {

    lateinit var strUs: String
    lateinit var strCor: String
    lateinit var strMsj: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto)

        val btnContacto = findViewById<Button>(R.id.btIniciatContacto)
        var eTContactoUsuarios = findViewById<EditText>(R.id.eTUsuarioContacto)
        val eTContactCorreo = findViewById<EditText>(R.id.eTCorreoContacto)
        val eTContactoMensaje = findViewById<EditText>(R.id.eTMensajeContacto)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = "Contacto"
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        btnContacto.setOnClickListener {
            if (eTContactCorreo.text.isEmpty() || eTContactoUsuarios.text.isEmpty() || eTContactoMensaje.text.isEmpty()) {
                DialogAlerta.crearDialogo(this@ContactoActivity, "", "Los campos no deben de estan vacios", "Alerta")
            } else {
                if(Utils.isEmailValid(""+eTContactCorreo.text)){
                    if (Utils.verifyAvailableNetwork(this)){
                        strUs = eTContactoUsuarios.text.toString()
                        strCor = eTContactCorreo.text.toString()
                        strMsj = eTContactoMensaje.text.toString()
                        getContacto().execute()
                    }else{
                       DialogAlerta.crearDialogo(this@ContactoActivity, "", "¡No cuenta con conexión a Internet!", "Alerta")
                    }
                }else{
                     DialogAlerta.crearDialogo(this@ContactoActivity, "", "El correo no cumple con el formato", "Alerta")
                }
            }
        }
    }


    //Inner class Async to consume Ws
    @SuppressLint("StaticFieldLeak")
    inner class getContacto: AsyncTask<Void, Void, String>() {

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
                getString(R.string.ws_url_contacto) + "getContacto&nombre=" + strUs + "&correo=" + strCor + "&mensaje=" + strMsj,
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
                    DialogAlerta.crearDialogo(this@ContactoActivity,"", "Gracias por enviarnos sus comentarios","Alerta")
                }else{
                    DialogAlerta.crearDialogo(this@ContactoActivity,"", "Datos incorrectos","Alerta")
                }
            }catch (e: JSONException){
                DialogAlerta.crearDialogo(this@ContactoActivity,"", "Lo sentimos, algo salio mal, ¡Intenta de nuevo!","Alerta")
                e.printStackTrace()
            }
        }
    }


}

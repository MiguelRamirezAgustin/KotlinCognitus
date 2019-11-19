package com.example.cognitus.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import com.example.cognitus.R
import com.example.cognitus.task.ApiGetPostHelper
import com.example.cognitus.utilities.Utils
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var strUsr: String
    lateinit var strNip: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val btnAceptar = findViewById<Button>(R.id.btnLogin)
        val eTContraseña = findViewById<EditText>(R.id.eTContraseña)
        val eTUsuario = findViewById<EditText>(R.id.eTUsuario)
        val imgContraseña = findViewById<ImageView>(R.id.imgContraseña)


        //ocultar y mostrar contraseña
        imgContraseña.setOnClickListener{
            if (eTContraseña.transformationMethod is PasswordTransformationMethod) {
                eTContraseña.transformationMethod = null
            } else {
                eTContraseña.transformationMethod = PasswordTransformationMethod()
            }
            eTContraseña.setSelection(eTContraseña.length())
        }

        //Evento de boton aceptar
        btnAceptar.setOnClickListener{
            if(eTContraseña.text.isEmpty() || eTUsuario.text.isEmpty()){
                Toast.makeText(this@MainActivity, "Verifique que los campos no esten vacios", Toast.LENGTH_SHORT).show()
            }else{
                if (Utils.isEmailValid(""+ eTUsuario.text)){
                    /*
                    //aguarda datos para sesion
                    val sharedPreferences = getSharedPreferences("my_aplication_cognius", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("usr_id", "100")
                    editor.commit()

                    val intent = Intent(this@MainActivity, MenuActivity::class.java)
                    intent.putExtra("nombre_usr", ""+ eTUsuario.text)
                    startActivity(intent)
                    finish()*/
                    //oculta reclado  y llamando al servicio
                    if(Utils.verifyAvailableNetwork(this)) {
                        val imm =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                        strUsr = eTUsuario.text.toString()
                        strNip = eTContraseña.text.toString()
                        getUsuarioIngreso().execute()
                    }else{
                       Toast.makeText(
                           applicationContext,
                           "¡No cuenta con conexión a Internet!",
                           Toast.LENGTH_SHORT
                       ).show()
                    }

                }else{
                    Toast.makeText(this@MainActivity, "El correo no cumple cpm el formato", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }



    //Inner class Async to consume Ws
    @SuppressLint("StaticFieldLeak")
    inner class getUsuarioIngreso: AsyncTask<Void, Void, String>() {

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
                getString(R.string.ws_url_login) + "getLoginUsr&email=" + strUsr + "&nip=" + strNip,
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
            //crear JSONObject a partir de una respuesta de cadena
               /*  respuesta de json de login
                 {
                  "valido": "1",
                      "usuario": {
                      "usr_id": "1",
                      "usr_nombre": "Juanito"
                        }
                  }
                */
                val rootJsonObject = JSONObject(results)
                val validoObject = rootJsonObject.getString("valido")
                if (validoObject == "1") {
                    val usuarioObject = rootJsonObject.getString("usuario")
                    val mItemObject = JSONObject(usuarioObject)

                    val usrIdS = mItemObject.getString("usr_id")
                    val usrNombre = mItemObject.getString("usr_nombre")

                    val sharendPreference = getSharedPreferences("my_aplication_cognius", Context.MODE_PRIVATE)
                    var editor =  sharendPreference.edit()
                    editor.putString("usr_id", usrIdS)
                    editor.putString("usr_name", usrNombre)
                    editor.commit()

                    val intent = Intent(applicationContext, MenuActivity::class.java)
                    intent.putExtra("usr_nombre", usrNombre)
                    intent.putExtra("usr_id", usrIdS)
                    startActivity(intent)
                    finish()

                }else{
                 Toast.makeText(applicationContext,
                     "Datos incorrectos",
                     Toast.LENGTH_SHORT
                 ).show()
                }
            }catch (e:JSONException){
                Toast.makeText(applicationContext,
                    "Lo sentimos, algo salio mal, ¡Intenta de nuevo!",
                    Toast.LENGTH_SHORT
                ).show()
                e.printStackTrace()
            }
        }
    }



}

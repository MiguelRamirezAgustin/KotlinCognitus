package com.example.cognitus.activities

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitus.R
import com.example.cognitus.adapter.AcercaDeAdapter
import com.example.cognitus.adapter.NoticiaAdapter
import com.example.cognitus.model.AcercaDeModel
import com.example.cognitus.model.NoticiaModel
import com.example.cognitus.task.ApiGetPostHelper
import com.example.cognitus.utilities.Utils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class AcercaDeActivity : AppCompatActivity() {

    //Declarate global variables
    lateinit var rvAcercaDe: RecyclerView
    lateinit var pBar: ProgressBar
    lateinit var tvMsg: TextView
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca_de)


        // maping of elements
        pBar = findViewById(R.id.pBar)
        tvMsg = findViewById(R.id.tvMsg)
        rvAcercaDe = findViewById(R.id.rvAcercaDe)

        val actionBar= supportActionBar
        if (actionBar != null){
            actionBar.title = "Acerca de"
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        //verify if we have internet connection
        if (Utils.verifyAvailableNetwork(this)) {
            // Call AsyncTask for getting list from server in JSON format
            getAcercaDe().execute()
        } else {
            Toast.makeText(
                applicationContext,
                "¡No cuenta con conexión a Internet!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    // show backbutton on actionbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    // Inner class Async to consume WS
    @SuppressLint("StaticFieldLeak")
    inner class getAcercaDe : AsyncTask<Void, Void, String>() {

        override fun onPreExecute() {
            // show progressbar for UI experience
            pBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg voids: Void): String {
            // Here is post Json api example
            val sendParams = HashMap<String, String>()
            // Send parameters and value on JSON api
            sendParams["Name"] = ""
            // send the HttpPostRequest by HttpURLConnection and receive a Results in return string
            return ApiGetPostHelper.SendParams(getString(R.string.ws_url_acercaDe), sendParams)
        }

        //respuesta del servicio
        override fun onPostExecute(results: String?) {
            // Hide Progressbar
            pBar.visibility = View.GONE
            tvMsg.visibility = View.VISIBLE

            if (results != null) {
                // See Response in Logcat for understand JSON Results
                Log.i("Resultado: ", results)
            }

            try {
                val listaAcercaDe = ArrayList<AcercaDeModel>()
                // create JSONObject from string response
                val rootJsonObject = JSONObject(results)
                // val isSucess = rootJsonObject.getString("nosotros")
                val acercaDeObject = rootJsonObject.getString("nosotros")
                val acercaDeArray = JSONArray(acercaDeObject)
                for (i in 0 until acercaDeArray.length()) {
                    // Get single JSON object node
                    val sObject = acercaDeArray.get(i).toString()
                    val mItemObject = JSONObject(sObject)
                    // Get String value from json object
                    val acercaDeId = mItemObject.getString("nosotros_id")
                    val acercaDeImg =  mItemObject.getString("nosotros_img")
                    val acercaDeUrl = mItemObject.getString("nosotros_status")
                    val acercaDeTitulo = mItemObject.getString("nosotros_desc")
                    val objeto = AcercaDeModel(acercaDeId, acercaDeImg, acercaDeUrl, acercaDeTitulo)
                    listaAcercaDe.add(objeto)
                }

                linearLayoutManager = LinearLayoutManager(applicationContext)
                val rvAcercaDe = findViewById<RecyclerView>(R.id.rvAcercaDe)
                rvAcercaDe!!.addItemDecoration(
                    DividerItemDecoration(
                        applicationContext,
                        LinearLayoutManager.VERTICAL
                    )
                )
                rvAcercaDe.layoutManager = linearLayoutManager
                rvAcercaDe.adapter = AcercaDeAdapter(this@AcercaDeActivity, listaAcercaDe)
            } catch (e: JSONException) {
                Toast.makeText(
                    applicationContext,
                    "Lo sentimos, algo salio mal. ¡Intenta de nuevo!",
                    Toast.LENGTH_SHORT
                ).show()
                e.printStackTrace()
            }
        }
    }
}

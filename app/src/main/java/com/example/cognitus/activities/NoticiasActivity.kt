package com.example.cognitus.activities

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitus.R
import com.example.cognitus.adapter.NoticiaAdapter
import com.example.cognitus.model.NoticiaModel
import com.example.cognitus.task.ApiGetPostHelper
import com.example.cognitus.utilities.Utils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class NoticiasActivity : AppCompatActivity() {

    //Declarate global variables
    lateinit var rvNoticias: RecyclerView
    lateinit var pBar: ProgressBar
    lateinit var tvMsg: TextView
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)

        // maping of elements
        pBar = findViewById(R.id.pBar)
        tvMsg = findViewById(R.id.tvMsg)
        rvNoticias = findViewById(R.id.rvNoticias)

        // show backbutton and set custom title on actionbar
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Listado de Noticias"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        //verify if we have internet connection
        if (Utils.verifyAvailableNetwork(this)) {
            // Call AsyncTask for getting list from server in JSON format
            getNoicias().execute()
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
    inner class getNoicias : AsyncTask<Void, Void, String>() {

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
        return ApiGetPostHelper.SendParams(getString(R.string.ws_url), sendParams)
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
            val listaNoticias = ArrayList<NoticiaModel>()
            // create JSONObject from string response
            val rootJsonObject = JSONObject(results)
            // val isSucess = rootJsonObject.getString("noticias")
            val noticiasObject = rootJsonObject.getString("noticias")
            val noticiasArray = JSONArray(noticiasObject)
            for (i in 0 until noticiasArray.length()) {
                // Get single JSON object node
                val sObject = noticiasArray.get(i).toString()
                val mItemObject = JSONObject(sObject)
                // Get String value from json object
                val notId = mItemObject.getString("not_id")
                val notImg = getString(R.string.img_url) + mItemObject.getString("not_img")
                val notUrl = mItemObject.getString("not_url")
                val notTitulo = mItemObject.getString("not_titulo")
                val notFecha = mItemObject.getString("not_fecha")
                val notDesc = mItemObject.getString("not_desc")
                val objeto = NoticiaModel(notId, notImg, notUrl, notTitulo, notFecha, notDesc)
                listaNoticias.add(objeto)
            }

            linearLayoutManager = LinearLayoutManager(applicationContext)
            val rvNoticias = findViewById<RecyclerView>(R.id.rvNoticias)
            rvNoticias!!.addItemDecoration(
                DividerItemDecoration(
                    applicationContext,
                    LinearLayoutManager.VERTICAL
                )
            )
            rvNoticias.layoutManager = linearLayoutManager
            rvNoticias.adapter = NoticiaAdapter(this@NoticiasActivity, listaNoticias)
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
package com.example.cognitus.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.cognitus.R

class Splash : AppCompatActivity(),Runnable {

        private lateinit var handler:Handler;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        simulaCarga()
    }

    private fun simulaCarga(){
        handler =  Handler()
        handler.postDelayed(this, 3000)
    }

    override fun run() {

        //valida si existe sesion
        val sharedPreferences = getSharedPreferences(
            "my_aplication_cognius",
            Context.MODE_PRIVATE
        )

        val usrId = sharedPreferences.getString("usr_id", "")
        Log.i("Valor", "-->" + usrId)
        //existe sesion
        if(!usrId.equals("")){
            val intent = Intent(this@Splash, MenuActivity::class.java)
            intent.putExtra("nombre_usr", ""+usrId)
            startActivity(intent)
            finish()
        }else{
            //no hay sesion
            val intent = Intent(this@Splash, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}

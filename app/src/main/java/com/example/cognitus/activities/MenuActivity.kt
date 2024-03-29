package com.example.cognitus.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.cognitus.R
import com.example.cognitus.utilities.DialogAlerta

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        supportActionBar?.hide()

        val eTUserParams = findViewById<TextView>(R.id.tVUserParam)
        val btnCerrar = findViewById<Button>(R.id.btnCierra)
        val LiNoticia = findViewById<LinearLayout>(R.id.LiNoticias)
        val LiProducto = findViewById<LinearLayout>(R.id.LiProducto)
        val LiContato = findViewById<LinearLayout>(R.id.LiContacto)
        val LiAcercaDe = findViewById<LinearLayout>(R.id.LiAcercaDe)

        val data = intent.getStringExtra("nombre_usr")

        eTUserParams.setText(data)

        btnCerrar.setOnClickListener{
            //cerrar sesion y elimina shareActionProvider

            /*DialogAlerta.creaAlerta(this@MenuActivity,
                "","¿Desea cerrar la sesion ?","Alert",2,true,true)*/

            val shareActionProvider = getSharedPreferences("my_aplication_cognius",Context.MODE_PRIVATE)
            var editor = shareActionProvider.edit()
            editor.putString("usr_id", "")
            editor.commit()
            val intent =  Intent(this@MenuActivity, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        //Evento de noticias
        LiNoticia.setOnClickListener {
            val intent = Intent(this@MenuActivity, NoticiasActivity::class.java )
            startActivity(intent)
        }

        //evento Producto
        LiProducto.setOnClickListener {
            val intent = Intent(this@MenuActivity, ProductosActivity::class.java)
            startActivity(intent)
        }

        //Evento contacto
        LiContato.setOnClickListener {
            startActivity(Intent(this@MenuActivity, ContactoActivity::class.java))
        }

        //Evento Acerca de
        LiAcercaDe.setOnClickListener {
            startActivity(Intent(this@MenuActivity, AcercaDeActivity::class.java))
        }

    }
}

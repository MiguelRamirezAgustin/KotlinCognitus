package com.example.cognitus.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cognitus.R
import com.example.cognitus.utilities.Utils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAceptar = findViewById<Button>(R.id.btnLogin)
        val eTContraseña = findViewById<EditText>(R.id.eTContraseña)
        val eTUsuario = findViewById<EditText>(R.id.eTUsuario)
        val This = this@MainActivity

        btnAceptar.setOnClickListener{
            if(eTContraseña.text.isEmpty() || eTUsuario.text.isEmpty()){
                Toast.makeText(This, "Verifique que los campos no esten vacios", Toast.LENGTH_SHORT).show()
            }else{
                if (Utils.isEmailValid(""+ eTUsuario.text)){
                    val intent = Intent(This, MenuActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(This, "El correo no cumple cpm el formato", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


}

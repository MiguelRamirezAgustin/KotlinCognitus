package com.example.cognitus.activities

import android.content.Context
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


        //Evento de boton aceptar
        btnAceptar.setOnClickListener{
            if(eTContraseña.text.isEmpty() || eTUsuario.text.isEmpty()){
                Toast.makeText(this@MainActivity, "Verifique que los campos no esten vacios", Toast.LENGTH_SHORT).show()
            }else{
                if (Utils.isEmailValid(""+ eTUsuario.text)){
                    //aguarda datos para sesion
                    val sharedPreferences = getSharedPreferences("my_aplication_cognius", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("usr_id", "100")
                    editor.commit()

                    val intent = Intent(this@MainActivity, MenuActivity::class.java)
                    intent.putExtra("nombre_usr", ""+ eTUsuario.text)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this@MainActivity, "El correo no cumple cpm el formato", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


}

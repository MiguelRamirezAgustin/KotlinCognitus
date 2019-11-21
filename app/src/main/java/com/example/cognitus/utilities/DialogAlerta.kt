package com.example.cognitus.utilities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.example.cognitus.R
import com.example.cognitus.activities.MenuActivity
import kotlinx.android.synthetic.main.view_dialog.view.*

object DialogAlerta {
    // true y true muestra dos botones
    // true y false muestra boton Aceptar

    fun creaAlerta(context: Context, titulo: String, mensaje: String, tituloAD: String, tipo: Int,btnAcp: Boolean,
                   btnC: Boolean) {
        crearDialogo(context, titulo, mensaje, tituloAD, tipo, btnAcp, btnC)
    }

    fun creaAlerta(context: Context, titulo: String, mensaje: String, tituloAD: String) {
        crearDialogo(context, titulo, mensaje, tituloAD, 0, true, false)
    }

    fun creaAlerta(context: Context, titulo: String, mensaje: String) {
        crearDialogo(context, titulo, mensaje, "", 0, false, false)
    }

    fun crearDialogo(
        context: Context,
        titulo: String,
        mensaje: String,
        tituloAD: String,
        tipo: Int,
        btnAcp: Boolean,
        btnC: Boolean
    ) {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.view_dialog, null)
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(context).setView(mDialogView)
        mDialogView.tvMsg?.text = mensaje
        mDialogView.tvTitulo?.text = titulo

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle(tituloAD)
        // show alert dialog
        alert.show()
        if (btnAcp) {
            mDialogView.btnAcepta.visibility = View.VISIBLE
            mDialogView.btnAcepta.setOnClickListener {
            //dismiss dialog

            if (tipo == 1) {
                alert.dismiss()
            } else {
                val intent = Intent(context, MenuActivity::class.java)
                context.startActivity(intent)
            }
        }}
        if (btnC) {
            mDialogView.btnCancel.visibility = View.VISIBLE
            //cancel button click of custom layout
            mDialogView.btnCancel.setOnClickListener {
                //dismiss dialog
                alert.hide()
            }
        }
    }
}
package com.example.cognitus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitus.R
import com.example.cognitus.activities.NoticiasActivity
import kotlinx.android.synthetic.main.items_noticia.view.*

class NoticiaAdapter (private val context:NoticiasActivity, private val chaptersList: ArrayList<String>) :
    RecyclerView.Adapter<NoticiaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.items_noticia, parent, false))
    }

    override fun getItemCount(): Int {
        return chaptersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tituloNoticia?.text = chaptersList.get(position)

        if(position==0)
            holder.imgNoticia?.setImageResource(R.drawable.img_1)
        if(position==1)
            holder.imgNoticia?.setImageResource(R.drawable.img_1)
        if(position==2)
            holder.imgNoticia?.setImageResource(R.drawable.img_1)

        holder.itemView.setOnClickListener {
            Toast.makeText(context, chaptersList.get(position), Toast.LENGTH_LONG).show()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tituloNoticia = view.tVTitulo!!
        val fechaNoticia = view.tVFecha!!
        val imgNoticia = view.imgNoticias!!
    }
}
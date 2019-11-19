package com.example.cognitus.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognitus.R
import com.example.cognitus.adapter.NoticiaAdapter

class NoticiasActivity : AppCompatActivity() {
    val noticiaList: ArrayList<String> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)

        setTitle("Casas disponibles")

        noticiaList.add("Noticia 1")
        noticiaList.add("Noticia 2")
        noticiaList.add("Noticia 3")

        linearLayoutManager = LinearLayoutManager(this)

        val rvCasas = findViewById<RecyclerView>(R.id.rvNoticias)
        rvCasas!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rvCasas.layoutManager = linearLayoutManager
        rvCasas.adapter = NoticiaAdapter(this, noticiaList)
    }
}

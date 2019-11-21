package com.example.cognitus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cognitus.R
import com.example.cognitus.activities.AcercaDeActivity
import com.example.cognitus.activities.NoticiasActivity
import com.example.cognitus.model.AcercaDeModel
import com.example.cognitus.model.NoticiaModel
import kotlinx.android.synthetic.main.item_acercade.view.*

class AcercaDeAdapter (private val context: AcercaDeActivity, private val acercaDeList:List<AcercaDeModel>) :
    RecyclerView.Adapter<AcercaDeAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_acercade, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return acercaDeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val acercaDeModel = acercaDeList.get(position)
        holder.descAcercaDe?.text = acercaDeModel.nosotrosDesc
        val requesManager=  Glide.with(context)
        val imgUrl = acercaDeModel.nosotrosImg
        val requesBuilder = requesManager.load(imgUrl)
        requesBuilder.into(holder.imgAcercaDe)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val descAcercaDe = view.tVAcercaDe
        val imgAcercaDe = view.imgAcercaDe
    }


}
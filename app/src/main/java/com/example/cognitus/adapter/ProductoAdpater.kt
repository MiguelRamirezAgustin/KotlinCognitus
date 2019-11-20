package com.example.cognitus.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cognitus.R
import com.example.cognitus.activities.DetalleNoticiaActivity
import com.example.cognitus.activities.NoticiasActivity
import com.example.cognitus.activities.ProductosActivity
import com.example.cognitus.model.NoticiaModel
import com.example.cognitus.model.ProductoModel
import kotlinx.android.synthetic.main.item_producto.view.*
import kotlinx.android.synthetic.main.items_noticia.view.*

class ProductoAdpater (private val context: ProductosActivity, private val productoList:List<ProductoModel>) :
    RecyclerView.Adapter<ProductoAdpater.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false))
    }

    override fun getItemCount(): Int {
        return productoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productoModel =productoList.get(position)
        holder.tituloProducto?.text = productoModel.prodTit
        holder.precioProducto?.text = productoModel.prodPre
        val requesManager=  Glide.with(context)
        val imgUrl = productoModel.prodImg
        val requesBuilder = requesManager.load(imgUrl)
        requesBuilder.into(holder.fotoProducto)
        holder.itemView.setOnClickListener {
           /* val intent = Intent(holder.itemView.context, DetalleNoticiaActivity::class.java)
            intent.putExtra("tituloNoticia", noticiaModel.notTitulo)
            intent.putExtra("detalleNoticia", noticiaModel.notDesc)
            intent.putExtra("imagenNoticia", noticiaModel.notImg)
            intent.putExtra("url_noticia", noticiaModel.notUrl)
            context.startActivity(intent)*/
        }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tituloProducto = view.tvTituloProducto
        val precioProducto = view.tvPrecioProducto
        val fotoProducto = view.ivFotoProducto
    }
}

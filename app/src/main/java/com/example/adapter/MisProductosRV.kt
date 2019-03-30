package com.example.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.superfoods.Producto
import com.example.superfoods.R
import com.example.superfoods.Receta
import com.google.firebase.firestore.FirebaseFirestore

class MisProductosRV (
    private val mProdsList: MutableList<Producto>,
    private val context: Context,
    private val firestoreDB: FirebaseFirestore) : RecyclerView.Adapter<MisProductosRV.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            val view = LayoutInflater.from(p0!!.context).inflate(R.layout.item_receta, p0, false)

            return ViewHolder(view)
        }
        private var listener: onItemClickListener? = null
        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            val note = mProdsList[p1]

            p0!!.title.text = note.nombre
            p0.content.text = note.precio

        }

        override fun getItemCount(): Int {
            return mProdsList.size
        }

        inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
            internal var title: TextView
            internal var content: TextView

            init {
                title = view.findViewById(R.id.txtnombre)
                content = view.findViewById(R.id.txtcategoria)
                view.setOnClickListener {
                    listener!!.onItemClick(mProdsList.get(adapterPosition))
                }
            }
        }

        interface onItemClickListener {
            fun onItemClick(contact: Producto)
        }

        fun setOnItemClickListener(listener: onItemClickListener) {
            this.listener = listener
        }
}
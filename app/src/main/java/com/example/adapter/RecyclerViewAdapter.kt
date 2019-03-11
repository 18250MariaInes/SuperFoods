package com.example.superfoods.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.superfoods.R
import com.example.superfoods.Receta
import com.google.firebase.firestore.FirebaseFirestore

class RecyclerViewAdapter(
    //private var listener:onItemClickListener?=null,
    private val recetasList: MutableList<Receta>,
    private val context: Context,
    private val firestoreDB: FirebaseFirestore
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0!!.context).inflate(R.layout.item_receta, p0, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val note = recetasList[p1]

        p0!!.title.text = note.nombre
        p0.content.text = note.categoria

    }

    override fun getItemCount(): Int {
        return recetasList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var title: TextView
        internal var content: TextView
        init {
            title = view.findViewById(R.id.txtnombre)
            content = view.findViewById(R.id.txtcategoria)
        }
    }
    interface onItemClickListener{
        fun onItemClick(contact: Receta)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        //this.listener=listener
    }
}

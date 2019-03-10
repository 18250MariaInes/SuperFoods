 package com.example.superfoods

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.superfoods.adapter.RecyclerViewAdapter
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_todas_recetas.*

 class TodasRecetas : AppCompatActivity() {
    private val TAG = "MainActivity"

    private var mAdapter: RecyclerViewAdapter? = null

    private var us: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todas_recetas)
        val correo = intent.getStringExtra("CORREO")
        us = FirebaseFirestore.getInstance()

        loadAllRecetas(us!!)

        /*mAdapter!!.setOnItemClickListener(object :RecyclerViewAdapter.onItemClickListener{
            override fun onItemClick(contact: Receta){
                var intent= Intent(baseContext, MostrarReceta::class.java)
                intent.putExtra(MostrarReceta.EXTRA_NOMBRE, contact.nombre)
                intent.putExtra(MostrarReceta.EXTRA_NUMERO, contact.ingredientes)
                intent.putExtra(MostrarReceta.EXTRA_CORREO, contact.categoria)
                intent.putExtra(MostrarReceta.EXTRA_PRIORITY, contact.proceso)
                startActivityForResult(intent, 1)
            }
        })*/

       /* mAdapter = RecyclerViewAdapter(recetasList, applicationContext, us!!)
        RecetasList.adapter = mAdapter*/
    }
     override fun onCreateOptionsMenu(menu: Menu): Boolean {
         menuInflater.inflate(R.menu.menu_main, menu)

         return super.onCreateOptionsMenu(menu)
     }
     override fun onDestroy() {
         super.onDestroy()

         firestoreListener!!.remove()
     }

     fun loadAllRecetas(mFirebaseFirestore: FirebaseFirestore){
         val correo = intent.getStringExtra("CORREO")
         mFirebaseFirestore.collection("users").whereEqualTo("correo", correo).get()
             .addOnSuccessListener(OnSuccessListener { documentSnapshots ->
                 if (documentSnapshots.isEmpty) {
                     Log.e(TAG, "onSuccess: LIST EMPTY")
                     return@OnSuccessListener
                 } else {
                     val recetasList = mutableListOf<Receta>()
                     val types = documentSnapshots.toObjects(User::class.java)
                     var recetas=types[0].recetas
                     for (doc in recetas!!){
                        recetasList.add(doc)
                     }
                     mAdapter = RecyclerViewAdapter(recetasList, applicationContext, us!!)
                     val mLayoutManager = LinearLayoutManager(applicationContext)
                     RecetasList.layoutManager = mLayoutManager
                     RecetasList.itemAnimator = DefaultItemAnimator()
                     RecetasList.adapter = mAdapter

                     Log.e(TAG, "onSuccess: " + types[0].Nombre!!)

                 }


             })

     }
     override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         if (item != null) {
             mAdapter!!.setOnItemClickListener(object :RecyclerViewAdapter.onItemClickListener{
                 override fun onItemClick(contact: Receta){
                     var intent= Intent(baseContext, Mis_Recetas::class.java)
                     intent.putExtra(Mis_Recetas.EXTRA_NOMBRE, contact.nombre)
                     intent.putExtra(Mis_Recetas.EXTRA_NUMERO, contact.ingredientes)
                     intent.putExtra(Mis_Recetas.EXTRA_CORREO, contact.categoria)
                     intent.putExtra(Mis_Recetas.EXTRA_PRIORITY, contact.proceso)
                     startActivityForResult(intent, 1)
                 }
             })


         }
         return super.onOptionsItemSelected(item)
     }
}

package com.example.superfoods

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.adapter.MisProductosRV
import com.example.superfoods.adapter.RecyclerViewAdapter
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_todas_recetas.*
import kotlinx.android.synthetic.main.activity_todos_mis_productos.*

class TodosMisProductos : AppCompatActivity() {
    private var mAdapter: MisProductosRV? = null
    //private var mAdapterp: ProductosRecyclerView? = null

    private var us: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todos_mis_productos)

        val correo = intent.getStringExtra("CORREO")
        us = FirebaseFirestore.getInstance()

        loadAllRecetas(us!!)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_opciones, menu)

        return super.onCreateOptionsMenu(menu)
    }
    override fun onDestroy() {
        super.onDestroy()

        firestoreListener!!.remove()
    }
    fun clickAdapter()
    {
        mAdapter!!.setOnItemClickListener(object :MisProductosRV.onItemClickListener{
            override fun onItemClick(contact: Producto){
                var intent= Intent(baseContext, MostrarProducto::class.java)
                intent.putExtra(MostrarProducto.EXTRA_NOMBREP, contact.nombre)
                intent.putExtra(MostrarProducto.EXTRA_DESC, contact.descripcion)
                intent.putExtra(MostrarProducto.EXTRA_PRECIO, contact.precio)
                intent.putExtra(MostrarProducto.EXTRA_CONTACTO, contact.contacto)
                startActivityForResult(intent, 1)
            }
        })
    }

    fun loadAllRecetas(mFirebaseFirestore: FirebaseFirestore){
        val correo = intent.getStringExtra("CORREO")
        mFirebaseFirestore.collection("users").whereEqualTo("correo", correo).get()
            .addOnSuccessListener(OnSuccessListener { documentSnapshots ->
                if (documentSnapshots.isEmpty) {
                    Log.e("hello", "onSuccess: LIST EMPTY")
                    return@OnSuccessListener
                } else {
                    val recetasList = mutableListOf<Producto>()
                    val types = documentSnapshots.toObjects(User::class.java)
                    var recetas=types[0].productos
                    for (doc in recetas!!){
                        recetasList.add(doc)
                    }
                    mAdapter = MisProductosRV(recetasList, applicationContext, us!!)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    mLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    mProdList.layoutManager = mLayoutManager
                    mProdList.itemAnimator = DefaultItemAnimator()
                    mProdList.setHasFixedSize(true)
                    mProdList.adapter = mAdapter
                    clickAdapter()
                    Log.e("hello", "onSuccess: " + types[0].Nombre!!)

                }


            })

    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            mAdapter!!.setOnItemClickListener(object :MisProductosRV.onItemClickListener{
                override fun onItemClick(contact: Producto){
                    var intent= Intent(baseContext, MostrarProducto::class.java)
                    intent.putExtra(MostrarProducto.EXTRA_NOMBREP, contact.nombre)
                    intent.putExtra(MostrarProducto.EXTRA_DESC, contact.descripcion)
                    intent.putExtra(MostrarProducto.EXTRA_PRECIO, contact.precio)
                    intent.putExtra(MostrarProducto.EXTRA_CONTACTO, contact.contacto)
                    startActivityForResult(intent, 1)
                }
            })
            val id = item!!.getItemId()

            if (id == R.id.Perfil) {
                val correo = intent.getStringExtra("CORREO")
                val intent = Intent(applicationContext, ShowProfileActivity::class.java)
                intent.putExtra("CORREO", correo)
                startActivityForResult(intent, 1)
                return true
            }else if (id == R.id.Mis_Recetas) {
                val correo = intent.getStringExtra("CORREO")
                val intent = Intent(applicationContext, TodasRecetas::class.java)
                intent.putExtra("CORREO", correo)
                startActivityForResult(intent, 1)
                return true
            }else if (id==R.id.Buscar_Productos){
                val correo = intent.getStringExtra("CORREO")
                val intent = Intent(this, BuscarProductos::class.java)
                intent.putExtra("CORREO", correo)
                startActivityForResult(intent,1)
            }

        }
        return super.onOptionsItemSelected(item)
    }

}

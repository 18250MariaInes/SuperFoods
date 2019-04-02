package com.example.superfoods

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.adapter.ProductosRecyclerView
import com.example.superfoods.GuardarReceta
import com.example.superfoods.R
import com.example.superfoods.User
import com.example.superfoods.adapter.RecyclerViewAdapter
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_buscar_productos.*
import kotlinx.android.synthetic.main.activity_todas_recetas.*

class BuscarProductos : AppCompatActivity() {
    private lateinit var txtproducto: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private var mAdapter: ProductosRecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_productos)
        txtproducto=findViewById(R.id.productotxt)
        database= FirebaseFirestore.getInstance()
        database.collection("productos").get().addOnSuccessListener { OnSuccessListener<QuerySnapshot>() {
            Log.e("SHOW",it.toObjects(User::class.java).size.toString())

        }}
        database.collection("productos").get()
            .addOnSuccessListener(OnSuccessListener { documentSnapshots ->
                if (documentSnapshots.isEmpty) {
                   // Log.e("555555", "onSuccess: LIST EMPTY")
                    return@OnSuccessListener
                } else {
                    val typ0s = documentSnapshots.toObjects(Producto::class.java)
                    mAdapter = ProductosRecyclerView(typ0s, applicationContext, database!!)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    mLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    productosList.layoutManager = mLayoutManager
                    productosList.itemAnimator = DefaultItemAnimator()
                    productosList.setHasFixedSize(true)
                    productosList.adapter = mAdapter
                    clickAdapter()
                    //Log.e("55", "onSuccess: ")

                }


            })
    }
    fun buscarProducto(view: View){
        val name:String=txtproducto.text.toString()
        database= FirebaseFirestore.getInstance()
        database.collection("productos").get().addOnSuccessListener { OnSuccessListener<QuerySnapshot>() {
            Log.e("SHOW",it.toObjects(User::class.java).size.toString())

        }}
        database.collection("productos").whereEqualTo("nombre", name).get()
            .addOnSuccessListener(OnSuccessListener { documentSnapshots ->
                if (documentSnapshots.isEmpty) {
                    Toast.makeText(baseContext, "Producto no disponible", Toast.LENGTH_SHORT).show()
                    Log.e("555555", "onSuccess: LIST EMPTY")
                    return@OnSuccessListener
                } else {
                    val prodList = mutableListOf<Producto>()
                    val types = documentSnapshots.toObjects(Producto::class.java)

                    mAdapter = ProductosRecyclerView(types, applicationContext, database!!)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    mLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    productosList.layoutManager = mLayoutManager
                    productosList.itemAnimator = DefaultItemAnimator()
                    productosList.setHasFixedSize(true)
                    productosList.adapter = mAdapter
                    clickAdapter()
                    Log.e("55", "onSuccess: ")

                }
            })
    }

    fun crearProducto(view:View){
        val correo = intent.getStringExtra("CORREO")
        val intent = Intent(this, CrearProducto::class.java)
        intent.putExtra("CORREO", correo)
        startActivityForResult(intent, 1)
    }
    fun clickAdapter()
    {
        mAdapter!!.setOnItemClickListener(object :ProductosRecyclerView.onItemClickListener{
            override fun onItemClick(contact: Producto){
                val correo = intent.getStringExtra("CORREO")
                var intent= Intent(baseContext, MostrarProducto::class.java)
                intent.putExtra("CORREO", correo)
                intent.putExtra(MostrarProducto.EXTRA_NOMBREP, contact.nombre)
                intent.putExtra(MostrarProducto.EXTRA_DESC, contact.descripcion)
                intent.putExtra(MostrarProducto.EXTRA_PRECIO, contact.precio)
                intent.putExtra(MostrarProducto.EXTRA_CONTACTO, contact.contacto)
                startActivityForResult(intent, 1)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_opciones, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
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
        }else if (id==R.id.Mis_Productos){
            val correo = intent.getStringExtra("CORREO")
            val intent = Intent(this, TodosMisProductos::class.java)
            intent.putExtra("CORREO", correo)
            startActivityForResult(intent,1)
        }


        return super.onOptionsItemSelected(item)
    }
}

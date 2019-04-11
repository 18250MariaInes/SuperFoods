package com.example.superfoods

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.adapter.ProductosRecyclerView
import com.example.superfoods.adapter.RecyclerViewAdapter
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_todas_recetas.*
import java.text.FieldPosition
import java.util.ArrayList

class TodasRecetas : AppCompatActivity() {
    var correop = ""
    private val TAG = "Mis Recetas"

    private var mAdapter: RecyclerViewAdapter? = null
    //private var mAdapterp: ProductosRecyclerView? = null

    private var us: FirebaseFirestore? = null


    private var firestoreListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todas_recetas)
        val correo = intent.getStringExtra("CORREO")
        //correop=correo
        us = FirebaseFirestore.getInstance()

        loadAllRecetas(us!!)
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                us!!.collection("users").whereEqualTo("correo", correo).get()
                    .addOnSuccessListener(OnSuccessListener { documentSnapshots ->
                        if (documentSnapshots.isEmpty) {
                            Log.e(TAG, "onSuccess: LIST EMPTY")
                            return@OnSuccessListener
                        } else {
                            val types = documentSnapshots.toObjects(User::class.java)
                            var recetas=types[0].recetas
                            recetas!!.removeAt(viewHolder.adapterPosition)
                            val frankDocRef = us!!.collection("users").document(documentSnapshots.documents.get(0).id).update("recetas",types[0].recetas)
                            loadAllRecetas(us!!)
                        }


                    })

                Toast.makeText(baseContext, "Se ha eliminado receta exitosamente", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(RecetasList)

    }


    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {



        super.onDestroy()
    }
    fun clickAdapter()
    {
        mAdapter!!.setOnItemClickListener(object :RecyclerViewAdapter.onItemClickListener{
            override fun onItemClick(contact: Receta,position:Int){
                val correo = intent.getStringExtra("CORREO")

                var intent= Intent(baseContext, MostrarReceta::class.java)
                intent.putExtra(MostrarReceta.EXTRA_NOMBRE, contact.nombre)
                intent.putExtra(MostrarReceta.EXTRA_INGREDIENTES, contact.ingredientes)
                intent.putExtra(MostrarReceta.EXTRA_CATEGORIA, contact.categoria)
                intent.putExtra(MostrarReceta.EXTRA_PROCESO, contact.proceso)
                intent.putExtra("CORREO", correo)
                intent.putExtra("pos",position)
                startActivityForResult(intent, 1)
                finish()
            }
        })
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
                    mLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    RecetasList.layoutManager = mLayoutManager
                    RecetasList.itemAnimator = DefaultItemAnimator()
                    RecetasList.setHasFixedSize(true)
                    RecetasList.adapter = mAdapter
                    clickAdapter()
                    Log.e(TAG, "onSuccess: " + types[0].Nombre!!)

                }


            })

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.getItemId()

        if (id == R.id.home) {
            val correo = intent.getStringExtra("CORREO")
            val intent = Intent(applicationContext, ShowProfileActivity::class.java)
            intent.putExtra("CORREO", correo)
            startActivityForResult(intent, 1)
            return true
        }


        return super.onOptionsItemSelected(item)
    }
}

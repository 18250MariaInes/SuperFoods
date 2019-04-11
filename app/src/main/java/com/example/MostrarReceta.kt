package com.example.superfoods

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.example.superfoods.adapter.RecyclerViewAdapter
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_mostrar_receta.*


class MostrarReceta : AppCompatActivity() {
    private lateinit var txtnombre: EditText
    private lateinit var txtcateg: EditText
    private lateinit var txtingredientes: EditText
    private lateinit var txtproceso: EditText
    private var us: FirebaseFirestore? = null
    var nombre=""

    companion object {
        const val EXTRA_ID = "com.example.maria.laboratorio7.EXTRA_ID"
        const val EXTRA_NOMBRE = "com.example.maria.laboratorio7.EXTRA_NOMBRE"
        const val EXTRA_INGREDIENTES = "com.example.maria.laboratorio7.EXTRA_INGREDIENTES"
        const val EXTRA_PROCESO = "com.example.maria.laboratorio7.EXTRA_PROCESO"
        const val EXTRA_CATEGORIA = "com.example.maria.laboratorio7.EXTRA_CATEGORIA"
        const val EXTRA_CORREO = "com.example.maria.laboratorio7.EXTRA_CORREO"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_receta)

        txtnombre = findViewById(R.id.nombretxt)
        txtcateg = findViewById(R.id.categtxt)

        txtingredientes = findViewById(R.id.ingtxt)
        txtproceso = findViewById(R.id.proctxt)
        //txtcateg=findViewById(R.id.txtcategoria)
        txtnombre.setText(intent.getStringExtra(EXTRA_NOMBRE))
        txtcateg.setText(intent.getStringExtra(EXTRA_CATEGORIA))
        txtingredientes.setText(intent.getStringExtra(EXTRA_INGREDIENTES))
        txtproceso.setText(intent.getStringExtra(EXTRA_PROCESO))

    }
    fun guardarN(view: View){
        nombre=intent.getStringExtra(EXTRA_NOMBRE)
        val correo = intent.getStringExtra("CORREO")
        val pos=intent.getIntExtra("pos",0)
        Log.e("hello",pos.toString())
        us = FirebaseFirestore.getInstance()
        var Nombren=nombretxt.text.toString()
        var catn=categtxt.text.toString()
        var ingn=ingtxt.text.toString()
        var procn=proctxt.text.toString()
        var nn=intent.getStringExtra(EXTRA_NOMBRE)
        val old=Receta(intent.getStringExtra(EXTRA_NOMBRE), intent.getStringExtra(EXTRA_CATEGORIA),intent.getStringExtra(EXTRA_INGREDIENTES), intent.getStringExtra(EXTRA_PROCESO))
//        Log.e("UUDI",correo)
        us!!.collection("users").whereEqualTo("correo", correo).get()
            .addOnSuccessListener(OnSuccessListener { documentSnapshots ->
                if (documentSnapshots.isEmpty) {
                    Log.e("hello", "onSuccess: LIST EMPTY")
                    return@OnSuccessListener
                } else {
                    val types = documentSnapshots.toObjects(User::class.java)
                    var recetas=types[0].recetas
                   /* val array: ArrayList<Receta> = arrayListOf()
                    for (doc in recetas!!){
                        if (doc.nombre==nn){
                            array.add(doc)
                        }
                    }
                    array.forEach {doc->
                        recetas.remove(doc)
                    }
                    agregar(recetas,Receta(nombre,catn,ingn,procn) )*/
                    recetas!!.set(pos,Receta(Nombren,catn,ingn,procn) )
                    val frankDocRef = us!!.collection("users").document(documentSnapshots.documents.get(0).id).update("recetas",types[0].recetas)
                    goback()
                }
            })


    }
    fun goback(){
        val correo = intent.getStringExtra("CORREO")
        val intent = Intent(applicationContext, TodasRecetas::class.java)
        intent.putExtra("CORREO", correo)
        startActivityForResult(intent, 1)
        finish()
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


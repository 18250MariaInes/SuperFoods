package com.example.superfoods

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.example.superfoods.adapter.RecyclerViewAdapter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class GuardarReceta : AppCompatActivity() {
    private var mAdapter: RecyclerViewAdapter? = null
    private lateinit var txtnombre: EditText
    private lateinit var txtcategoria: EditText
    private lateinit var txtingredientes: EditText
    private lateinit var txtproceso: EditText
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardar_receta)

        txtnombre=findViewById(R.id.nombretxt)
        txtcategoria=findViewById(R.id.categoritatxt)
        txtingredientes=findViewById(R.id.ingretxt)
        txtproceso=findViewById(R.id.procesotxt)

    }

    fun guardarReceta(view:View){
        val correo = intent.getStringExtra("CORREO")
        val us = FirebaseFirestore.getInstance()
        us.collection("users").get().addOnSuccessListener { OnSuccessListener<QuerySnapshot>()
        {
            Log.e("SHOW",it.toObjects(User::class.java).size.toString())

        }}

        us.collection("users").whereEqualTo("correo", correo).get()
            .addOnSuccessListener(OnSuccessListener { documentSnapshots ->
                if (documentSnapshots.isEmpty) {

                    return@OnSuccessListener
                } else {
                    documentSnapshots.documents.get(0)
                    val types = documentSnapshots.toObjects(User::class.java)
                    types[0].recetas!!.add(Receta(txtnombre.text.toString(), txtcategoria.text.toString(), txtingredientes.text.toString(), txtproceso.text
                        .toString()))
                    Log.e("oooo", "onSuccess: " + types[0].recetas!!.size!!+" "+ documentSnapshots.documents.get(0).id)
                    //val id=types[0].id

                    auth= FirebaseAuth.getInstance()
                    val frankDocRef = us.collection("users").document(documentSnapshots.documents.get(0).id).update("recetas",types[0].recetas)
                    //val frankDocRef = us.collection("users").document(documentSnapshots.documents.get(0).id).delete("recetas",types[0].recetas)

                }
            }).addOnCompleteListener(OnCompleteListener {task->
                if (task.isSuccessful()) {
                    for (f in task.getResult()!!.getDocuments()) {
                        // here you can get the id.
                        val client = f.toObject(User::class.java)//(f.getId())
                        // you can apply your actions...
                        Log.e("COMPLETE",f.id)
                    }
                    action()
                }
            })


    }
    private fun action(){
        val correo = intent.getStringExtra("CORREO")
        val intent = Intent(applicationContext, ShowProfileActivity::class.java)
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
                finish()
                return true
            }


        return super.onOptionsItemSelected(item)
    }
}

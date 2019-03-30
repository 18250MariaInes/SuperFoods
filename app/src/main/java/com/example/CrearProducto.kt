package com.example.superfoods

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.superfoods.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class CrearProducto : AppCompatActivity() {
    private lateinit var txtnombre: EditText
    private lateinit var txtdescripcion: EditText
    private lateinit var txtprecio: EditText
    private lateinit var txtcontacto: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseFirestore
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_producto)

        txtnombre=findViewById(R.id.nombreptxt)
        txtdescripcion=findViewById(R.id.desctxt)
        txtprecio=findViewById(R.id.preciotxt)
        //txtcontacto=findViewById(R.id.contactotxt)
        database= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
    }

    fun Crear(view:View){
        createProduct()
    }

    private fun createProduct(){
        val name:String=txtnombre.text.toString()
        val lastName:String=txtdescripcion.text.toString()
        val email:String=txtprecio.text.toString()
        //var password:String=txtcontacto.text.toString()
        val correo = intent.getStringExtra("CORREO")
        val password=correo

        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(lastName)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
                        val prod=Producto(name, lastName, email, password).toMap()
                        database!!.collection("productos")
                            .add(prod)
                            .addOnSuccessListener { documentReference ->

                                Toast.makeText(applicationContext, "Se ha creado exitosamente!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->

                                Toast.makeText(applicationContext, "ERROR!", Toast.LENGTH_SHORT).show()
                            }


            val correo = intent.getStringExtra("CORREO")
            val us = FirebaseFirestore.getInstance()
            us.collection("users").get().addOnSuccessListener { OnSuccessListener<QuerySnapshot>()
            {
                Log.e("SHOW",it.toObjects(User::class.java).size.toString())

            }
            }

            us.collection("users").whereEqualTo("correo", correo).get()
                .addOnSuccessListener(OnSuccessListener { documentSnapshots ->
                    if (documentSnapshots.isEmpty) {

                        return@OnSuccessListener
                    } else {
                        documentSnapshots.documents.get(0)
                        val types = documentSnapshots.toObjects(User::class.java)
                        types[0].productos!!.add(Producto(name, lastName ,email ,password))
                        //Log.e("oooo", "onSuccess: " + types[0].productos!!.size!!+" "+ documentSnapshots.documents.get(0).id)
                        //val id=types[0].id

                        auth= FirebaseAuth.getInstance()
                        val frankDocRef = us.collection("users").document(documentSnapshots.documents.get(0).id).update("productos",types[0].productos)

                    }
                })
            action()

        }


    }
    private fun action(){
        val correo = intent.getStringExtra("CORREO")
        val intent = Intent(this, BuscarProductos::class.java)
        intent.putExtra("CORREO", correo)
        startActivityForResult(intent,1)
        //startActivity(Intent(this, BuscarProductos::class.java))
    }
}

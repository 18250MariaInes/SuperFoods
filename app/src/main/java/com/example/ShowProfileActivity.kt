package com.example.superfoods

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ShowProfileActivity : AppCompatActivity() {
    private lateinit var txtNombre: TextView
    private lateinit var txtApellido: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_profile)

        auth= FirebaseAuth.getInstance()
        txtNombre=findViewById(R.id.txtNombre)
        txtApellido=findViewById(R.id.txtapellido)

        loadUserInfo()
    }

    fun loadUserInfo(){
        val user: FirebaseUser? =auth.currentUser
        val us = FirebaseFirestore.getInstance()
        us.collection("users").get().addOnSuccessListener { OnSuccessListener<QuerySnapshot>() {
            Log.e("SHOW",it.toObjects(User::class.java).size.toString())

        }}
        getuserinfo(us)
        if (user != null) {
            if (user.displayName!=null) {

                var Nombre = user.displayName
                txtNombre.text = Nombre
            }
        }
    }
    fun getuserinfo(mFirebaseFirestore: FirebaseFirestore) {
        val TAG = "SS"
        val correo = intent.getStringExtra("CORREO")
        mFirebaseFirestore.collection("users").whereEqualTo("correo", correo).get()
            .addOnSuccessListener(OnSuccessListener { documentSnapshots ->
                if (documentSnapshots.isEmpty) {
                    Log.e(TAG, "onSuccess: LIST EMPTY")
                    return@OnSuccessListener
                } else {
                    val types = documentSnapshots.toObjects(User::class.java)

                    txtNombre.text = types[0].Nombre!!
                    txtApellido.text=types[0].Correo!!
                    Log.e(TAG, "onSuccess: " + types[0].Nombre!!)
                }
            })
        auth= FirebaseAuth.getInstance()
        //val frankDocRef = mFirebaseFirestore.collection("users").document("qN7ZV23a4ccyTysXn6Qv").update("nombre","")

    }
    fun crearReceta(view:View){
        val correo = intent.getStringExtra("CORREO")
        val intent = Intent(applicationContext, GuardarReceta::class.java)
        intent.putExtra("CORREO", correo)
        startActivityForResult(intent, 1)
        finish()
    }
    fun mostrarRecetas(view: View){
        val correo = intent.getStringExtra("CORREO")
        val intent = Intent(applicationContext, TodasRecetas::class.java)
        intent.putExtra("CORREO", correo)
        startActivityForResult(intent, 1)
        finish()
    }
}

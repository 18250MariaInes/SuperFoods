package com.example.superfoods

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.view.MenuInflater
import android.view.MenuItem


class MostrarReceta : AppCompatActivity() {
    private lateinit var txtnombre: TextView
    private lateinit var txtcateg: TextView
    companion object {
        const val EXTRA_ID = "com.example.maria.laboratorio7.EXTRA_ID"
        const val EXTRA_NOMBRE = "com.example.maria.laboratorio7.EXTRA_NOMBRE"
        const val EXTRA_NUMERO = "com.example.maria.laboratorio7.EXTRA_NUMERO"
        const val EXTRA_PRIORITY = "com.example.maria.laboratorio7.EXTRA_PRIORITY"
        const val EXTRA_CORREO = "com.example.maria.laboratorio7.EXTRA_CORREO"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_receta)

        txtnombre=findViewById(R.id.txtnombre)
        //txtcateg=findViewById(R.id.txtcategoria)
        txtnombre.setText(intent.getStringExtra(EXTRA_NOMBRE))
    }

}

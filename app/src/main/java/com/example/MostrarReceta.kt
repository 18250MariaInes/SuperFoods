package com.example.superfoods

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.view.MenuInflater
import android.view.MenuItem
import com.example.superfoods.adapter.RecyclerViewAdapter


class MostrarReceta : AppCompatActivity() {
    private lateinit var txtnombre: TextView
    private lateinit var txtcateg: TextView
    private lateinit var txtingredientes: TextView
    private lateinit var txtproceso: TextView

    companion object {
        const val EXTRA_ID = "com.example.maria.laboratorio7.EXTRA_ID"
        const val EXTRA_NOMBRE = "com.example.maria.laboratorio7.EXTRA_NOMBRE"
        const val EXTRA_INGREDIENTES = "com.example.maria.laboratorio7.EXTRA_INGREDIENTES"
        const val EXTRA_PROCESO = "com.example.maria.laboratorio7.EXTRA_PROCESO"
        const val EXTRA_CATEGORIA = "com.example.maria.laboratorio7.EXTRA_CATEGORIA"
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

   /* override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_opciones, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            /*    mAdapter!!.setOnItemClickListener(object :RecyclerViewAdapter.onItemClickListener{
                    override fun onItemClick(contact: Receta){
                        var intent= Intent(baseContext, MostrarReceta::class.java)
                        intent.putExtra(MostrarReceta.EXTRA_NOMBRE, contact.nombre)
                        intent.putExtra(MostrarReceta.EXTRA_INGREDIENTES, contact.ingredientes)
                        intent.putExtra(MostrarReceta.EXTRA_CATEGORIA, contact.categoria)
                        intent.putExtra(MostrarReceta.EXTRA_PROCESO, contact.proceso)
                        startActivityForResult(intent, 1)
                    }
                })*/
            val id = item!!.getItemId()

            if (id == R.id.Perfil) {
                val correo = intent.getStringExtra("CORREO")
                val intent = Intent(applicationContext, ShowProfileActivity::class.java)
                intent.putExtra("CORREO", correo)
                startActivityForResult(intent, 1)
                return true
            } else if (id == R.id.Mis_Recetas) {
                val correo = intent.getStringExtra("CORREO")
                val intent = Intent(applicationContext, TodasRecetas::class.java)
                intent.putExtra("CORREO", correo)
                startActivityForResult(intent, 1)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }*/
}


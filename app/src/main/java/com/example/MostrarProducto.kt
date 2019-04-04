package com.example.superfoods

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MostrarProducto : AppCompatActivity() {
    private lateinit var txtnombre: TextView
    private lateinit var txtdescripcion: TextView
    private lateinit var txtprecio: TextView
    private lateinit var txtcontacto: TextView
    var filpath: Uri? = null
    companion object {
        const val EXTRA_NOMBREP = "com.example.maria.laboratorio7.EXTRA_NOMBREP"
        const val EXTRA_DESC = "com.example.maria.laboratorio7.EXTRA_DESC"
        const val EXTRA_PRECIO = "com.example.maria.laboratorio7.EXTRA_PRECIO"
        const val EXTRA_CONTACTO = "com.example.maria.laboratorio7.EXTRA_CONTACTO"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_producto)
        txtnombre = findViewById(R.id.nombretxt)
        txtdescripcion = findViewById(R.id.desctxt)
        val correo=intent.getStringExtra(EXTRA_CONTACTO)

        txtprecio = findViewById(R.id.preciotxt)
        txtcontacto = findViewById(R.id.contactotxt)
        //txtcateg=findViewById(R.id.txtcategoria)
        txtnombre.setText(intent.getStringExtra(EXTRA_NOMBREP))
        txtdescripcion.setText(intent.getStringExtra(EXTRA_DESC))
        txtprecio.setText(intent.getStringExtra(EXTRA_PRECIO))
        txtcontacto.setText(intent.getStringExtra(EXTRA_CONTACTO))

        txtcontacto .setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.type = "text/html"
            intent.data= Uri.parse("mailto:"+correo)
            intent.putExtra(Intent.EXTRA_EMAIL,intent.getStringExtra(EXTRA_CONTACTO))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Interes en tu producto")
            intent.putExtra(Intent.EXTRA_TEXT, "Hola, estoy interesad@ en tu producto, espero tu respuesta")

            startActivity(Intent.createChooser(intent, "Send Email"))
            intent.action = "com.example.superfoods.CUSTOM_INTENT";
            sendBroadcast(intent)

        }
    }
}

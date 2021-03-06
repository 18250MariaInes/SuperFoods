package com.example.superfoods

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.common.collect.TreeTraverser.using





class MostrarProducto : AppCompatActivity() {
    private lateinit var txtnombre: TextView
    private lateinit var txtdescripcion: TextView
    private lateinit var txtprecio: TextView
    private lateinit var txtcontacto: TextView
    private lateinit var imgprod: ImageView
    var filpath: Uri? = null
    companion object {
        const val EXTRA_NOMBREP = "com.example.maria.laboratorio7.EXTRA_NOMBREP"
        const val EXTRA_DESC = "com.example.maria.laboratorio7.EXTRA_DESC"
        const val EXTRA_PRECIO = "com.example.maria.laboratorio7.EXTRA_PRECIO"
        const val EXTRA_CONTACTO = "com.example.maria.laboratorio7.EXTRA_CONTACTO"
        const val EXTRA_IMG = "com.example.maria.laboratorio7.EXTRA_IMG"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_producto)
        var id=intent.getStringExtra(EXTRA_IMG)
        var dir="https://firebasestorage.googleapis.com/v0/b/superfoods-24093.appspot.com/o/images%2F"+id+"?alt=media&token=f7f15ebe-d6eb-4259-a7e9-952b107cb3e4"//gs://superfoods-24093.appspot.com/
        Log.e("mostrarProducto", dir)
        //imgprod=findViewById(R.id.imagenProd)
        val myWebView: WebView = findViewById(R.id.wb)
        myWebView.setInitialScale(150)
        myWebView.loadUrl(dir)
       /* val ref= FirebaseStorage.getInstance().getReference().child(dir)
        //imgprod.setImageBitmap(ref)
        //var islandRef = ref.child(dir)
        val ONE_MEGABYTE: Long = 1024 * 1024
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            //imgprod.setImageURI(ref)
        }.addOnFailureListener {
            // Handle any errors
        }*/

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

package com.example.superfoods

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_crear_producto.*
import java.util.*

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
        imagenProd.setOnClickListener {
            //Log.e("haha","funcionaaaa")
            val PICK_PHOTO_CODE = 1046
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type="image/*"
            startActivityForResult(intent,PICK_PHOTO_CODE)

        }
    }
    var selectedPhotoUri:Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( data !=null){
            Log.e("ssss","entre en selec "+data.data)

             selectedPhotoUri= data.data
            Log.e("SSS",selectedPhotoUri.toString())
            val bitmap= MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            imagenProd.setImageBitmap(bitmap)
        }
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
                        var img=uploadDB()
                        val prod=Producto(name, lastName, email, password,img ).toMap()
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
                        types[0].productos!!.add(Producto(name, lastName ,email ,password, img))
                        //Log.e("oooo", "onSuccess: " + types[0].productos!!.size!!+" "+ documentSnapshots.documents.get(0).id)
                        //val id=types[0].id

                        auth= FirebaseAuth.getInstance()
                        val frankDocRef = us.collection("users").document(documentSnapshots.documents.get(0).id).update("productos",types[0].productos)

                    }
                    //uploadDB()
                })
            //uploadDB()
            action()

        }


    }
    private fun uploadDB():String {
        if (selectedPhotoUri==null) return "holo"
        Log.e("LLEGO","DB")
        val filename= UUID.randomUUID().toString()
        Log.e("LLEGO",filename)
        val ref=FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.e("success","")
            }.addOnFailureListener { e ->
                Log.e("FALLO",e.toString())
                e.printStackTrace()
            }
        return filename

    }
    private fun action(){
        val correo = intent.getStringExtra("CORREO")
        val intent = Intent(this, BuscarProductos::class.java)
        intent.putExtra("CORREO", correo)
        startActivityForResult(intent,1)
        finish()
        //startActivity(Intent(this, BuscarProductos::class.java))
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

package com.example.superfoods

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ShowProfileActivity : AppCompatActivity() {
    private lateinit var txtNombre: EditText
    private lateinit var txtApellido: EditText
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
        var user:FirebaseUser= auth.getCurrentUser()!!

        if (user.displayName!=null) {

            var Nombre = user.displayName
            txtNombre.setText(Nombre)
        }
    }
}

package com.example.superfoods

import android.accessibilityservice.AccessibilityService
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.content.Intent
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

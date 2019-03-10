package com.example.superfoods

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_mis__recetas.*
import kotlinx.android.synthetic.main.app_bar_mis__recetas.*

class Mis_Recetas : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_mis__recetas)
        setSupportActionBar(toolbar)
        txtnombre=findViewById(R.id.txtnombre)
        //txtcateg=findViewById(R.id.txtcategoria)
        txtnombre.setText(intent.getStringExtra(Mis_Recetas.EXTRA_NOMBRE))


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.mis__recetas, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

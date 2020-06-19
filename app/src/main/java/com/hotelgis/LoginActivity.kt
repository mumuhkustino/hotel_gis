package com.hotelgis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.hotelgis.admin.ui.RegisterUserActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLoginUser = findViewById(R.id.btn_login_user) as Button
        btnLoginUser.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnLoginAdmin = findViewById(R.id.btn_login_admin) as Button
        btnLoginAdmin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnRegistrasiUser = findViewById(R.id.btn_register) as TextView
        btnRegistrasiUser.setOnClickListener {
            val intent = Intent(this, RegisterUserActivity::class.java)
            startActivity(intent)
        }
    }
}
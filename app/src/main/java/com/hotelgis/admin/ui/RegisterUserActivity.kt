package com.hotelgis.admin.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.hotelgis.R
import kotlinx.android.synthetic.main.activity_register_user.*

class RegisterUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        toolbar.title = "Register User"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

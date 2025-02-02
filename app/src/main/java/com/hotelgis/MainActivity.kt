package com.hotelgis

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hotelgis.admin.adapter.ListHotelAdapter
import com.hotelgis.admin.ui.AddEditHotelActivity
import com.hotelgis.admin.ui.AddEditRoomActivity
import com.hotelgis.admin.ui.DetailHotelActivity
import com.hotelgis.model.Hotel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_content.*
import kotlinx.android.synthetic.main.nav_header_content.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ListHotelAdapter.OnItemClickCallback {

    private lateinit var listHotelAdapter: ListHotelAdapter
    private val db = Firebase.firestore.collection("hotels")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Dashboard Admin"
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout,
            toolbar, 0, 0
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        navView.setCheckedItem(R.id.listHotel)

        recyclerViewHotel.layoutManager = LinearLayoutManager(this)
        listHotelAdapter = ListHotelAdapter(this)
        recyclerViewHotel.adapter = listHotelAdapter

        fabAddHotel.setOnClickListener {
            val intentToDetail = Intent(baseContext, AddEditHotelActivity::class.java)
            startActivity(intentToDetail)
        }

        retrieveHotels()

        showDisplayName()
    }

    private fun showDisplayName(){
        Firebase.firestore.collection("users")
            .whereEqualTo("uid",Firebase.auth.currentUser?.uid)
            .get()
            .addOnSuccessListener {
                if(it.documents.size!=0){
                    navView.getHeaderView(0).tvAccountName.text = it.documents[0].getString("username")
                }
            }
    }

    private fun retrieveHotels() {
        var hotels: ArrayList<Hotel> = arrayListOf()
        db.whereEqualTo("userid", Firebase.auth.currentUser?.uid.toString()).get().addOnSuccessListener {
            result ->
            for (document in result) {
                var hotel = document.toObject<Hotel>()
                hotels.add(hotel)
            }
            listHotelAdapter.listHotel = hotels
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.addHotel -> {
                val intentToDetail = Intent(baseContext, AddEditHotelActivity::class.java)
                startActivity(intentToDetail)
            }
            R.id.addRoom -> {
                val intentToDetail = Intent(baseContext, AddEditRoomActivity::class.java)
                intentToDetail.putParcelableArrayListExtra(AddEditRoomActivity.EXTRA_LIST_HOTEL, listHotelAdapter.listHotel)
                startActivity(intentToDetail)
            }
            R.id.logout -> {
                Firebase.auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onItemClicked(hotel: Hotel) {
        if (hotel != null) {
            val intentToDetail = Intent(baseContext, DetailHotelActivity::class.java)
            intentToDetail.putExtra(DetailHotelActivity.EXTRA_DETAIL_HOTEL, hotel)
            startActivity(intentToDetail)
        }
    }

    override fun onStart() {
        super.onStart()
        retrieveHotels()
    }

    override fun onResume() {
        super.onResume()
        navView.setCheckedItem(R.id.listHotel)
        retrieveHotels()
    }
}

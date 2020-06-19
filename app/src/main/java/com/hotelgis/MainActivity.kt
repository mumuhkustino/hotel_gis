package com.hotelgis

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.hotelgis.admin.adapter.ListHotelAdapter
import com.hotelgis.admin.ui.AddEditHotelActivity
import com.hotelgis.admin.ui.AddEditRoomActivity
import com.hotelgis.admin.ui.DetailHotelActivity
import com.hotelgis.model.Hotel
import com.hotelgis.model.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ListHotelAdapter.OnItemClickCallback {

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

        val listHotelAdapter = ListHotelAdapter(this)
        listHotelAdapter.listHotel = loadHotel()
        recyclerViewHotel.adapter = listHotelAdapter
    }

    private fun loadHotel(): List<Hotel> {
        //DATA DUMMY ROOM
        var rooms: ArrayList<Room> = ArrayList()
        rooms.add(Room("Special Place", "CODE132", "Room Name", 2, 200000, "imageUrl"))
        return mutableListOf(
            Hotel(
                "Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kec. Cicendo, Kota Bandung",
                "+62 87878787878",
                "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png",
                "0.0",
                "0.0",
                rooms
            ),
            Hotel(
                "Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kec. Cicendo, Kota Bandung",
                "+62 87878787878",
                "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png",
                "0.0",
                "0.0",
                rooms
            ),
            Hotel(
                "Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kec. Cicendo, Kota Bandung",
                "+62 87878787878",
                "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png",
                "0.0",
                "0.0",
                rooms
            ),
            Hotel(
                "Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kec. Cicendo, Kota Bandung",
                "+62 87878787878",
                "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png",
                "0.0",
                "0.0",
                rooms
            ),
            Hotel(
                "Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kec. Cicendo, Kota Bandung",
                "+62 87878787878",
                "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png",
                "0.0",
                "0.0",
                rooms
            ),
            Hotel(
                "Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kec. Cicendo, Kota Bandung",
                "+62 87878787878",
                "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png",
                "0.0",
                "0.0",
                rooms
            ),
            Hotel(
                "Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kota Bandung",
                "+62 87878787878",
                "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png",
                "0.0",
                "0.0",
                rooms
            )
        )
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.addHotel -> {
                val intentToDetail = Intent(baseContext, AddEditHotelActivity::class.java)
                startActivity(intentToDetail)
            }
            R.id.addRoom -> {
                val intentToDetail = Intent(baseContext, AddEditRoomActivity::class.java)
                startActivity(intentToDetail)
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

    override fun onResume() {
        super.onResume()
        navView.setCheckedItem(R.id.listHotel)
    }
}

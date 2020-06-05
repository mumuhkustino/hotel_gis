package com.hotelgis

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.hotelgis.admin.adapter.ListHotelAdapter
import com.hotelgis.model.Hotel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Dashboard Admin"
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout,
            toolbar, 0, 0)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        navView.setCheckedItem(R.id.listHotel)

        recyclerViewHotel.layoutManager = LinearLayoutManager(this)
        val listViewType = mutableListOf<Int>(
            ListHotelAdapter.TYPE_HEADER,
            ListHotelAdapter.TYPE_CONTENT,
            ListHotelAdapter.TYPE_CONTENT,
            ListHotelAdapter.TYPE_CONTENT,
            ListHotelAdapter.TYPE_CONTENT,
            ListHotelAdapter.TYPE_CONTENT,
            ListHotelAdapter.TYPE_CONTENT,
            ListHotelAdapter.TYPE_CONTENT
        )
        val listHotelAdapter = ListHotelAdapter(listViewType = listViewType)
        listHotelAdapter.listHotel = loadHotel()
        recyclerViewHotel.adapter = listHotelAdapter
    }

    private fun loadHotel(): List<Hotel> {
        return mutableListOf(
            Hotel("List Hotel", "", "", "", "", ""),
            Hotel("Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kec. Cicendo, Kota Bandung",
                "+62 87878787878", "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png", "0.0", "0.0"),
            Hotel("Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kec. Cicendo, Kota Bandung",
                "+62 87878787878", "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png", "0.0", "0.0"),
            Hotel("Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kec. Cicendo, Kota Bandung",
                "+62 87878787878", "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png", "0.0", "0.0"),
            Hotel("Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kec. Cicendo, Kota Bandung",
                "+62 87878787878", "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png", "0.0", "0.0"),
            Hotel("Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kec. Cicendo, Kota Bandung",
                "+62 87878787878", "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png", "0.0", "0.0"),
            Hotel("Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kec. Cicendo, Kota Bandung",
                "+62 87878787878", "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png", "0.0", "0.0"),
            Hotel("Hotel Hilton Bandung",
                "Jl. Raya ABCD EFGH No. 128, Kota Bandung",
                "+62 87878787878", "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png", "0.0", "0.0")
        )
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.addHotel -> {
                TODO()
            }
            R.id.addRoom -> {
                TODO()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}

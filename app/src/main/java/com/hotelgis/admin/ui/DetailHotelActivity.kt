package com.hotelgis.admin.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hotelgis.R
import com.hotelgis.model.Hotel
import com.hotelgis.model.Room
import com.roomgis.admin.adapter.ListRoomAdapter
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_detail_hotel.*

class DetailHotelActivity : AppCompatActivity() {

    companion object {
        val EXTRA_HOTEL = "EXTRA_HOTEL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_hotel)

        var hotel: Hotel? = null
        if (intent != null) {
            hotel = intent.getParcelableExtra(EXTRA_HOTEL)
        }

        toolbar.title = hotel?.name
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (hotel != null) {
            Glide.with(baseContext)
                .load(hotel.image)
                .error(R.drawable.ic_launcher_background)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)))
                .into(imgHotel)
            tvHotelName.text = hotel.name
            tvHotelAddress.text = hotel.address
            tvHotelPhone.text = hotel.phone
            tvHotelLocation.text = (hotel.lat + ", " + hotel.long)
        }

        recyclerViewRoom.layoutManager = LinearLayoutManager(this)

        val listHotelAdapter = ListRoomAdapter()
        listHotelAdapter.listRoom = loadRoom()
        recyclerViewRoom.adapter = listHotelAdapter
    }

    private fun loadRoom(): List<Room> {
        return mutableListOf(
            Room("Hotel Hilton Bandung",
                "F2810",
                "Family Suite",
                210,
                500000,
                "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png"),
            Room("Hotel Hilton Bandung",
                "F2810",
                "Family Suite",
                210,
                500000,
                "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png"),
            Room("Hotel Hilton Bandung",
                "F2810",
                "Family Suite",
                210,
                500000,
                "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png"),
            Room("Hotel Hilton Bandung",
                "F2810",
                "Family Suite",
                210,
                500000,
                "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png"),
            Room("Hotel Hilton Bandung",
                "F2810",
                "Family Suite",
                210,
                500000,
                "https://www.toptal.com/designers/subtlepatterns/patterns/memphis-mini.png")
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.hotelgis.admin.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hotelgis.R
import com.hotelgis.admin.adapter.ListRoomAdapter
import com.hotelgis.model.Hotel
import com.hotelgis.model.Room
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_detail_hotel.*

class DetailHotelActivity : AppCompatActivity(), ListRoomAdapter.OnItemClickCallback {

    companion object {
        const val EXTRA_DETAIL_HOTEL = "EXTRA_DETAIL_HOTEL"
    }

    private var hotel: Hotel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_hotel)

        if (intent != null) {
            hotel = intent.getParcelableExtra(EXTRA_DETAIL_HOTEL)
        }

        toolbar.title = hotel?.name
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (hotel != null) {
            Glide.with(baseContext)
                .load(hotel?.image)
                .error(R.drawable.ic_launcher_background)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)))
                .into(imgHotel)
            tvHotelName.text = hotel?.name
            tvHotelAddress.text = hotel?.address
            tvHotelPhone.text = hotel?.phone
            tvHotelLocation.text = (hotel?.lat + ", " + hotel?.long)

            btnEditHotel.setOnClickListener {
                val intentToEditHotel = Intent(baseContext, AddEditHotelActivity::class.java)
                intentToEditHotel.putExtra(AddEditHotelActivity.EXTRA_HOTEL, hotel)
                startActivity(intentToEditHotel)
            }

            recyclerViewRoom.layoutManager = LinearLayoutManager(this)

            val listRoomAdapter = ListRoomAdapter(this)
            listRoomAdapter.listRoom = hotel!!.rooms
            recyclerViewRoom.adapter = listRoomAdapter
        }

        fabAddRoom.setOnClickListener {
            val intentToAddRoom = Intent(baseContext, AddEditRoomActivity::class.java)
            intentToAddRoom.putExtra(AddEditRoomActivity.EXTRA_HOTEL, hotel)
            startActivity(intentToAddRoom)
        }
    }

    override fun onItemClicked(room: Room) {
        val intentToAddRoom = Intent(baseContext, AddEditRoomActivity::class.java)
        intentToAddRoom.putExtra(AddEditRoomActivity.EXTRA_HOTEL, hotel)
        intentToAddRoom.putExtra(AddEditRoomActivity.EXTRA_ROOM, room)
        startActivity(intentToAddRoom)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

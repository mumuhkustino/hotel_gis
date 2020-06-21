package com.hotelgis.admin.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hotelgis.PesanKamarActivity
import com.hotelgis.R
import com.hotelgis.admin.adapter.ListRoomUserAdapter
import com.hotelgis.model.Room
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_detail_room.*

class DetailRoomActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL_ROOM = "EXTRA_DETAIL_ROOM"
        const val EXTRA_LIST_ROOM = "EXTRA_LIST_ROOM"
    }

    private lateinit var room: Room
    private var listRoom: ArrayList<Room> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_room)

        room = intent.getParcelableExtra(EXTRA_DETAIL_ROOM)
        listRoom = intent.getParcelableArrayListExtra(EXTRA_LIST_ROOM)

        toolbar.title = room.name
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvHeaderListRoom.text = "Kamar lain di ${room.place}"
        Glide.with(baseContext)
            .load(room.image)
            .error(R.drawable.ic_launcher_background)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)))
            .into(imgRoom)
        tvRoomCost.text = "Harga : Rp. ${room.cost} / Malam"
        tvRoomQuantity.text = "Kamar Tersisa : ${room.quantity}"
        tvRoomFacilityDetail.text = room.facility

        btnBookRoom.setOnClickListener {
            val intentToEditHotel = Intent(baseContext, PesanKamarActivity::class.java)
            intentToEditHotel.putExtra(PesanKamarActivity.EXTRA_DETAIL_ROOM, room)
            startActivity(intentToEditHotel)
        }

        recyclerViewRoom.layoutManager = LinearLayoutManager(this)

        val listRoomUserAdapter = ListRoomUserAdapter()
        listRoomUserAdapter.listRoom = listRoom
        recyclerViewRoom.adapter = listRoomUserAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

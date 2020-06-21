package com.hotelgis.admin.ui

import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hotelgis.R
import com.hotelgis.model.Hotel
import com.hotelgis.model.Room
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_add_edit_hotel.*

class AddEditHotelActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HOTEL = "EXTRA_HOTEL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_hotel)

        var hotel: Hotel? = null

        if (intent != null) {
            hotel = intent.getParcelableExtra(EXTRA_HOTEL)
        }

        toolbar.title = resources.getString(R.string.add_data_hotel)
        if (hotel != null) {
            toolbar.title = resources.getString(R.string.edit_data_hotel)

            edtHotelName.text = Editable.Factory.getInstance().newEditable(hotel.name)
            edtHotelAddress.text = Editable.Factory.getInstance().newEditable(hotel.address)
            edtHotelPhone.text = Editable.Factory.getInstance().newEditable(hotel.phone)
            Glide.with(baseContext)
                .load(hotel.image)
                .error(R.drawable.ic_launcher_background)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)))
                .into(imgHotel)
            edtHotelLatitude.text = Editable.Factory.getInstance().newEditable(hotel.lat)
            edtHotelLongitude.text = Editable.Factory.getInstance().newEditable(hotel.long)
            btnAddDataHotel.text = resources.getString(R.string.edit_data_hotel)
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnBrowseImage.setOnClickListener {
//            tvImageName.text = //Name Image (ex: Image1.jpeg)
//            Glide.with(baseContext)
//                .load(image)
//                .error(R.drawable.ic_launcher_background)
//                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)))
//                .into(imgHotel)
        }

        btnAddDataHotel.setOnClickListener {
            // DATA DUMMY ROOM
            var rooms: ArrayList<Room> = ArrayList()
            rooms.add(Room("Special Place", "CODE132", "Room Name", 2, 200000, "Kasur 2 single, AC, Kamar mandi di dalam, tv layar datar, kedap suara, wifi gratis, peralatan mandi, telepon, sandal, ketel listrik, lemari, meja kerja", "imageUrl"))
            val hotel = Hotel(
                edtHotelName.text.toString(),
                edtHotelAddress.text.toString(),
                edtHotelPhone.text.toString(),
                imgHotel.toString(),
                edtHotelLatitude.text.toString(),
                edtHotelLongitude.text.toString(),
                rooms
            )
//            TODO("PUSH HOTEL DATA TO FIREBASE")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

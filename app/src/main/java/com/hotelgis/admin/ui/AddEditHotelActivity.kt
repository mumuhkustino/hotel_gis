package com.hotelgis.admin.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.hotelgis.R
import com.hotelgis.model.Hotel
import kotlinx.android.synthetic.main.activity_add_edit_hotel.*

class AddEditHotelActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HOTEL = "EXTRA_HOTEL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_hotel)

        toolbar.title = resources.getString(R.string.add_data_hotel)
        if (intent.getStringExtra(EXTRA_HOTEL) != null) {
            toolbar.title = resources.getString(R.string.edit_data_hotel)
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
            val hotel = Hotel(
                edtHotelName.text.toString(),
                edtHotelAddress.text.toString(),
                edtHotelPhone.text.toString(),
                imgHotel.toString(),
                edtHotelLatitude.text.toString(),
                edtHotelLongitude.text.toString()
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

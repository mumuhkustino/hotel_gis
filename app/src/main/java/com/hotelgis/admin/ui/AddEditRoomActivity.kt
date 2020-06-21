package com.hotelgis.admin.ui

import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hotelgis.R
import com.hotelgis.model.Room
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_add_edit_room.*

class AddEditRoomActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ROOM = "EXTRA_ROOM"
        const val EXTRA_HOTEL = "EXTRA_HOTEL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_room)

        var room: Room? = null
        var hotelName: String? = null

        if (intent != null) {
            hotelName = intent.getStringExtra(EXTRA_HOTEL)
            room = intent.getParcelableExtra(EXTRA_ROOM)
        }

        toolbar.title = resources.getString(R.string.add_data_room)
        if (hotelName != null && room != null) {
            toolbar.title = resources.getString(R.string.edit_data_room)

            spinnerView.setItems(arrayListOf(hotelName))
            spinnerView.selectItemByIndex(0)

            edtRoomCode.text = Editable.Factory.getInstance().newEditable(room.code)
            edtRoomName.text = Editable.Factory.getInstance().newEditable(room.name)
            edtRoomQuantity.text = Editable.Factory.getInstance().newEditable(room.quantity.toString())
            edtRoomCost.text = Editable.Factory.getInstance().newEditable(room.cost.toString())

            Glide.with(baseContext)
                .load(room.image)
                .error(R.drawable.ic_launcher_background)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)))
                .into(imgRoom)
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val nameHotel = intent.getStringExtra(EXTRA_HOTEL)

        btnBrowseImage.setOnClickListener {
//            tvImageName.text = //Name Image (ex: Image1.jpeg)
//            Glide.with(baseContext)
//                .load(image)
//                .error(R.drawable.ic_launcher_background)
//                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)))
//                .into(imgRoom)
        }

        btnAddDataRoom.setOnClickListener {
            val hotel = Room(
                intent.getStringExtra(EXTRA_HOTEL),
                edtRoomCode.text.toString(),
                edtRoomName.text.toString(),
                edtRoomQuantity.text.toString().toInt(),
                edtRoomCost.text.toString().toInt(),
                imgRoom.toString()
            )
            TODO("PUSH ROOM DATA TO FIREBASE")
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

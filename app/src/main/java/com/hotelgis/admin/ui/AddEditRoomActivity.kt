package com.hotelgis.admin.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hotelgis.R
import com.hotelgis.model.Hotel
import com.hotelgis.model.Room
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_add_edit_room.*
import kotlinx.android.synthetic.main.activity_add_edit_room.btnBrowseImage
import kotlinx.android.synthetic.main.activity_add_edit_room.toolbar

class AddEditRoomActivity : AppCompatActivity() {
    private val db: FirebaseFirestore = Firebase.firestore
    private var hotel: Hotel? = null
    private var room: Room? = null

    companion object {
        const val EXTRA_ROOM = "EXTRA_ROOM"
        const val EXTRA_HOTEL = "EXTRA_HOTEL"
        const val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_room)

        var hotelName: String? = null

        if (intent != null) {
            hotel = intent.getParcelableExtra(EXTRA_HOTEL)
            hotelName = hotel!!.name
            room = intent.getParcelableExtra(EXTRA_ROOM) as? Room
        }

        toolbar.title = resources.getString(R.string.add_data_room)
        if (hotelName != null && room != null) {
            toolbar.title = resources.getString(R.string.edit_data_room)

            spinnerView.setItems(arrayListOf(hotelName))
            spinnerView.selectItemByIndex(0)

            edtRoomCode.text = Editable.Factory.getInstance().newEditable(room?.code)
            edtRoomName.text = Editable.Factory.getInstance().newEditable(room?.name)
            edtRoomQuantity.text =
                Editable.Factory.getInstance().newEditable(room?.quantity.toString())
            edtRoomCost.text = Editable.Factory.getInstance().newEditable(room?.cost.toString())
            btnAddDataRoom.text = resources.getString(R.string.edit_data_room)

            Glide.with(baseContext)
                .load(room?.image)
                .error(R.drawable.ic_launcher_background)
                .apply(
                    RequestOptions.bitmapTransform(
                        RoundedCornersTransformation(
                            20,
                            0,
                            RoundedCornersTransformation.CornerType.ALL
                        )
                    )
                )
                .into(imgRoom)
            edtRoomFacility.text = Editable.Factory.getInstance().newEditable(room?.facility)
        } else {
            spinnerView.setItems(arrayListOf(hotelName))
            spinnerView.selectItemByIndex(0)
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnBrowseImage.setOnClickListener {
            if (!edtRoomName.text.toString().equals("")) {
                Intent(Intent.ACTION_GET_CONTENT).also {
                    it.type = "image/*"
                    startActivityForResult(it, AddEditRoomActivity.REQUEST_SELECT_IMAGE_IN_ALBUM)
                }
            } else {
                Toast.makeText(
                    baseContext,
                    "Nama Room harus diisi terlebih dahulu",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btnAddDataRoom.setOnClickListener {
            val newRoom = Room(
                hotelName!!,
                edtRoomCode.text.toString(),
                edtRoomName.text.toString(),
                edtRoomQuantity.text.toString().toInt(),
                edtRoomCost.text.toString().toInt(),
                edtRoomFacility.text.toString(),
                tvImageName.text.toString()
            )
            if (toolbar.title == resources.getString(R.string.add_data_room)) {
                Toast.makeText(this, "Click tambah kamar", Toast.LENGTH_SHORT).show()
                addRoom(newRoom)
            } else {
                editRoom(newRoom)
            }
        }
    }


    private fun addRoom(newRoom: Room) {
        val hotelsCollection = db.collection("hotels")
        hotelsCollection
            .whereEqualTo("name", hotel?.name)
            .whereEqualTo("lat", hotel?.lat)
            .whereEqualTo("long", hotel?.long)
            .get()
            .addOnSuccessListener {
                if (it.documents.size != 0) {
                    hotelsCollection.document(it.documents[0].id)
                        .update(
                            "rooms", FieldValue.arrayUnion(
                                mapOf(
                                    "place" to newRoom.place,
                                    "code" to newRoom.code,
                                    "name" to newRoom.name,
                                    "quantity" to newRoom.quantity,
                                    "cost" to newRoom.cost,
                                    "facility" to newRoom.facility,
                                    "image" to newRoom.image
                                )
                            )
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Add Room Successful", Toast.LENGTH_SHORT)
                                    .show()
                                finish()
                            } else {
                                Toast.makeText(this, "Add Room Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    Toast.makeText(this, "Success getting hotel data", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error getting hotel data", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("d", "Error getting documents: ", exception)
            }

    }

    private fun editRoom(newRoom: Room) {
        var docId: String
        val hotelsCollection = db.collection("hotels")
        hotelsCollection
            .whereEqualTo("name", hotel?.name)
            .whereEqualTo("lat", hotel?.lat)
            .whereEqualTo("long", hotel?.long)
            .get()
            .addOnSuccessListener {
                if (it.documents.size != 0) {
                    docId = it.documents[0].id
                    hotelsCollection.document(docId).update(
                        "rooms", FieldValue.arrayRemove(
                            mapOf(
                                "place" to room?.place,
                                "code" to room?.code,
                                "name" to room?.name,
                                "quantity" to room?.quantity,
                                "cost" to room?.cost,
                                "facility" to room?.facility,
                                "image" to room?.image
                            )
                        )
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Delete Room Successful", Toast.LENGTH_SHORT)
                                .show()
                            hotelsCollection.document(docId).update(
                                "rooms", FieldValue.arrayUnion(
                                    mapOf(
                                        "place" to newRoom.place,
                                        "code" to newRoom.code,
                                        "name" to newRoom.name,
                                        "quantity" to newRoom.quantity,
                                        "cost" to newRoom.cost,
                                        "facility" to newRoom.facility,
                                        "image" to newRoom.image
                                    )
                                )
                            ).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Update Room Successful",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    finish()
                                } else {
                                    Toast.makeText(this, "Update Room Failed", Toast.LENGTH_SHORT)
                                }
                            }
                        } else {
                            Toast.makeText(this, "Delete Room Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    //TODO tidak ada hotelnya
                }
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

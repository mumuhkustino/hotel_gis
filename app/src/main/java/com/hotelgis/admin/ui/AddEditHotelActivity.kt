package com.hotelgis.admin.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hotelgis.R
import com.hotelgis.model.Hotel
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_add_edit_hotel.*

class AddEditHotelActivity : AppCompatActivity() {

    private val db: FirebaseFirestore = Firebase.firestore
    private var curFile: Uri? = null
    private var curUrl: String? = null
    private var hotel: Hotel? = null

    companion object {
        const val EXTRA_HOTEL = "EXTRA_HOTEL"
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_hotel)


        if (intent != null) {
            hotel = intent.getParcelableExtra(EXTRA_HOTEL)
        }

        toolbar.title = resources.getString(R.string.add_data_hotel)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (hotel != null) {
            toolbar.title = resources.getString(R.string.edit_data_hotel)

            edtHotelName.text = Editable.Factory.getInstance().newEditable(hotel?.name)
            edtHotelAddress.text = Editable.Factory.getInstance().newEditable(hotel?.address)
            edtHotelPhone.text = Editable.Factory.getInstance().newEditable(hotel?.phone)
            Glide.with(baseContext)
                .load(hotel?.image)
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
                .into(imgHotel)
            val imgName: String = edtHotelName.text.toString().toLowerCase().replace("\\s".toRegex(), "_")
            tvImageName.text = "image_hotel_$imgName"
            edtHotelLatitude.text = Editable.Factory.getInstance().newEditable(hotel?.lat)
            edtHotelLongitude.text = Editable.Factory.getInstance().newEditable(hotel?.long)
            btnAddDataHotel.text = resources.getString(R.string.edit_data_hotel)
        }

        btnBrowseImage.setOnClickListener {
            if (!edtHotelName.text.toString().equals("")) {
                Intent(Intent.ACTION_GET_CONTENT).also {
                    it.type = "image/*"
                    startActivityForResult(it, REQUEST_SELECT_IMAGE_IN_ALBUM)
                }
            } else {
                Toast.makeText(
                    baseContext,
                    "Nama Hotel harus diisi terlebih dahulu",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btnAddDataHotel.setOnClickListener {
            if (!edtHotelName.text.toString().equals("") && !edtHotelName.text.toString().equals(null)) {
                if (!edtHotelAddress.text.toString().equals("") && !edtHotelAddress.text.toString().equals(null)) {
                    if (!edtHotelPhone.text.toString().equals("") && !edtHotelPhone.text.toString().equals(null)) {
                        if (!edtHotelLatitude.text.toString().equals("") && !edtHotelLatitude.text.toString().equals(null)) {
                            if (!edtHotelLongitude.text.toString().equals("") && !edtHotelLongitude.text.toString().equals(null)) {
                                if (!tvImageName.text.toString().equals("") && !tvImageName.text.toString().equals(null) && !tvImageName.text.toString().equals("Image1.jpeg")) {
                                    uploadImageToStorage(tvImageName.text.toString())
                                } else {
                                    Toast.makeText(baseContext, "Image Hotel kosong", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(baseContext, "Longitude Hotel kosong", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(baseContext, "Latitude Hotel kosong", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(baseContext, "No. Tlp Hotel kosong", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(baseContext, "Alamat Hotel kosong", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(baseContext, "Nama Hotel kosong", Toast.LENGTH_SHORT).show()
                Log.d("d", "Gawa:Ada yang null")
            }
        }
    }

    private fun updateHotelDataToFirestore(hotelNew: Hotel) {
        db.collection("hotels")
            .whereEqualTo("name", hotel?.name)
            .whereEqualTo("address", hotel?.address)
            .whereEqualTo("phone", hotel?.phone)
            .whereEqualTo("lat", hotel?.lat)
            .whereEqualTo("long", hotel?.long)
            .get()
            .addOnSuccessListener {
                if (it.documents.size != 0) {
                    db.collection("hotels").document(it.documents.get(0).id)
                        .update(
                            mapOf(
                                "name" to hotelNew.name,
                                "address" to hotelNew.address,
                                "image" to hotelNew.image,
                                "phone" to hotelNew.phone,
                                "lat" to hotelNew.lat,
                                "long" to hotelNew.long
                            )
                        ).addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Hotel has successfully been updated",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed to updated hotel", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }
                }
            }.addOnFailureListener { exception ->
                Log.w("AEHA", "Error updating documents: ", exception)
                Toast.makeText(this, "Failed to updated hotel", Toast.LENGTH_SHORT).show()
                finish()
            }
    }

    private fun uploadImageToStorage(fileName: String) {
        if (curFile != null) {
            Firebase.storage.reference.child("images/$fileName").delete()
                .addOnCompleteListener {
                    Firebase.storage.reference.child("images/$fileName").putFile(curFile!!)
                        .addOnCompleteListener {
                            Log.d("d", "Gawa:UploadComplete")
                        }.addOnSuccessListener {
                            Log.d("d", "Gawa:UploadSuccess")
                            Firebase.storage.reference.child("images/$fileName").downloadUrl
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        curUrl = task.result.toString()
                                    }
                                    //Ketika url berhasil didapat, lagsung add data to hotel
                                    val hotelNew = Hotel(
                                        Firebase.auth.currentUser?.uid.toString(),
                                        edtHotelName.text.toString(),
                                        edtHotelAddress.text.toString(),
                                        edtHotelPhone.text.toString(),
                                        curUrl.toString(),
                                        edtHotelLatitude.text.toString(),
                                        edtHotelLongitude.text.toString(),
                                        arrayListOf()
                                    )
                                    if (!btnAddDataHotel.text.toString()
                                            .equals(resources.getString(R.string.edit_data_hotel))
                                    ) {
                                        addHotelDataToFirestore(hotelNew)
                                        Log.d("d", "Gawa:Uploading image BERHASIL ADDHOTEL")
                                    } else {
                                        updateHotelDataToFirestore(hotelNew)
                                        Log.d("d", "Gawa:Uploading image BERHASIL UPDATEHOTEL")
                                    }
                                    //                        addHotelDataToFirestore(hotel)
                                    Log.d("", "Gawa:$curUrl")
                                }.addOnSuccessListener {
                                    Log.d("d", "Gawa:GETURL image BERHASIL")
                                }
                                .addOnFailureListener {

                                    Log.d("d", "Gawa:GETURL image GAGAL")
                                }
                        }
                }

        } else {
            val hotelNew = Hotel(
                Firebase.auth.currentUser?.uid.toString(),
                edtHotelName.text.toString(),
                edtHotelAddress.text.toString(),
                edtHotelPhone.text.toString(),
                hotel?.image.toString(),
                edtHotelLatitude.text.toString(),
                edtHotelLongitude.text.toString(),
                arrayListOf()
            )
            updateHotelDataToFirestore(hotelNew)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM) {
            data?.data?.let {
                curFile = it
                imgHotel.setImageURI(it)
                val imgName: String =
                    edtHotelName.text.toString().toLowerCase().replace("\\s".toRegex(), "_")
                tvImageName.text = "image_hotel_$imgName"
            }
        }
    }

    fun addHotelDataToFirestore(hotel: Hotel) {
        // Add a new document with a generated ID
        db.collection("hotels")
            .add(hotel)
            .addOnSuccessListener { documentReference ->
                Log.d("d", "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(this, "Hotel has successfully been added", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Log.w("d", "Error adding document", e)
                Toast.makeText(this, "Failed to add hotel", Toast.LENGTH_SHORT).show()
                finish()
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

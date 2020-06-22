package com.hotelgis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hotelgis.admin.adapter.ListRoomUserAdapter
import com.hotelgis.model.Hotel
import com.hotelgis.model.Room
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var tvHotelName: TextView
    private lateinit var tvHotelAddress: TextView
    private lateinit var tvHotelPhone: TextView
    private lateinit var imgHotel: ImageView
    private val db: FirebaseFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        tvHotelName = findViewById(R.id.tv_hotel_name)
        tvHotelAddress = findViewById(R.id.tv_hotel_address)
        tvHotelPhone = findViewById(R.id.tv_hotel_phone)
        imgHotel = findViewById(R.id.img_hotel)

        btn_more.setOnClickListener {
            if (btn_logout.visibility == View.VISIBLE) {
                btn_logout.visibility = View.GONE
            } else {
                btn_logout.visibility = View.VISIBLE
            }
        }
        btn_logout.setOnClickListener {
            logoutUser()
        }

        sliding_layout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
    }

    override fun onResume() {
        super.onResume()
        sliding_layout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapLoadedCallback {
            getHotels()
        }

        mMap.setOnMapClickListener {
            sliding_layout.panelHeight =
                resources.getDimensionPixelSize(R.dimen.sliding_layout_height_hide)
            if (btn_show_panel.visibility == View.GONE) {
                btn_show_panel.visibility = View.VISIBLE
            }
            if (btn_logout.visibility == View.VISIBLE) {
                btn_logout.visibility = View.GONE
            }
        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        if (btn_logout.visibility == View.VISIBLE) {
            btn_logout.visibility = View.GONE
        }
        //Show SlidePanel
        if (btn_show_panel.visibility == View.VISIBLE) {
            btn_show_panel.visibility = View.GONE
        }
        sliding_layout.panelHeight = resources.getDimensionPixelSize(R.dimen.sliding_layout_height)
        val hotel: Hotel = p0?.tag as Hotel
        tvHotelName.text = hotel.name
        tvHotelAddress.text = hotel.address
        tvHotelPhone.text = hotel.phone
        Glide.with(baseContext)
            .load(hotel.image)
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

        recyclerview_room_list.layoutManager = LinearLayoutManager(this)

        val listRoomUserAdapter = ListRoomUserAdapter()
        listRoomUserAdapter.listRoom = hotel.rooms
        recyclerview_room_list.adapter = listRoomUserAdapter
        return false
    }

    fun getHotels() {
        var hotels: ArrayList<Hotel> = ArrayList()
        db.collection("hotels")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var hotel = document.toObject<Hotel>()
                    hotels.add(hotel)
                }
                showMarkerOnMap(hotels)
            }
            .addOnFailureListener { exception ->
                Log.w("d", "Error getting documents: ", exception)
            }
    }

    private fun showMarkerOnMap(hotels: ArrayList<Hotel>) {
        var builder = LatLngBounds.builder()
        hotels.forEach {
            // Add a marker in Sydney and move the camera
            val latlong = LatLng(it.lat.toDouble(), it.long.toDouble())
            val marker: Marker = mMap.addMarker(MarkerOptions().position(latlong).title(it.name))
            marker.tag = it
            builder.include(marker.position)
        }
        var bounds: LatLngBounds = builder.build()
        var padding = 40 // offset from edges of the map in pixels
        var cu: CameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu)
        mMap.setOnMarkerClickListener(this)
    }

    fun logoutUser() {
        Firebase.auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
package com.hotelgis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hotelgis.admin.adapter.ListRoomAdapter
import com.hotelgis.model.Hotel
import com.hotelgis.model.Room
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_detail_hotel.*
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var tvHotelName: TextView
    private lateinit var tvHotelAddress: TextView
    private lateinit var tvHotelPhone: TextView
    private lateinit var imgHotel: ImageView

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

        var builder = LatLngBounds.builder()
        getHotels().forEach {
            // Add a marker in Sydney and move the camera
            val latlong = LatLng(it.lat.toDouble(), it.long.toDouble())
            val marker: Marker = mMap.addMarker(MarkerOptions().position(latlong).title(it.name))
            marker.tag = it
            builder.include(marker.position)
        }
        var bounds: LatLngBounds = builder.build()
        var padding = 40 // offset from edges of the map in pixels
        var cu: CameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.setOnMapLoadedCallback {
            mMap.animateCamera(cu)
        }
        mMap.setOnMarkerClickListener(this)

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        val hotel: Hotel = p0?.tag as Hotel
        tvHotelName.text = hotel.name
        tvHotelAddress.text = hotel.address
        tvHotelPhone.text = hotel.phone
        Glide.with(baseContext)
            .load(hotel.image)
            .error(R.drawable.ic_launcher_background)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)))
            .into(imgHotel)

        recyclerview_room_list.layoutManager = LinearLayoutManager(this)

        val listRoomAdapter = ListRoomAdapter()
        listRoomAdapter.listRoom = hotel.rooms
        recyclerview_room_list.adapter = listRoomAdapter
        return false
    }

    fun getHotels(): ArrayList<Hotel> {
        var hotels: ArrayList<Hotel> = ArrayList()
        var rooms: ArrayList<Room> = ArrayList()
        rooms.add(Room("Special Suite", "CODE132", "Room Name", 2, 200000, "imageUrl"))
        rooms.add(Room("Ordinary", "CODE133", "Room Name", 1, 100000, "imageUrl"))
        hotels.add(
            Hotel(
                "Mumuh Hotel",
                "Jalan Tasikmalaya",
                "022 6040032",
                "https://www.hrs.com/en/media/image/ff/97/72/Cape_Racha_Hotel_Serviced_Apartments-Si_Racha-Aussenansicht-452044_600x600.jpg",
                "0",
                "10",
                rooms
            )
        )
        hotels.add(
            Hotel(
                "Gawa Hotel",
                "Jalan Kinagara",
                "022 6044030",
                "https://www.hrs.com/en/media/image/ca/18/bf/Hotel_Equatorial_Ho_Chi_Minh_City-Ho-Chi-Minh-Stadt-Aussenansicht-2-62178_600x600.jpg",
                "0",
                "20",
                rooms
            )
        )
        hotels.add(
            Hotel(
                "Pajo Hotel",
                "Jalan Paledang",
                "022 6046030",
                "https://www.hrs.com/en/media/image/07/g0/2d/Sure_Hotel_by_Best_Western_Center-Goeteborg-Aussenansicht-2-375260_600x600.jpg",
                "0",
                "30",
                rooms
            )
        )
        return hotels
    }
}
package com.hotelgis.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hotel(
    var name: String = "",
    var address: String = "",
    var phone: String = "",
    var image: String = "",
    var lat: String = "",
    var long: String = "",
    var rooms: ArrayList<Room> = ArrayList()
) : Parcelable
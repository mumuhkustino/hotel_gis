package com.hotelgis.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Room(
    var place: String = "",
    var code: String = "",
    var name: String = "",
    var quantity: Int = 0,
    var cost: Int = 0,
    var facility: String = "",
    var image: String = ""
) : Parcelable
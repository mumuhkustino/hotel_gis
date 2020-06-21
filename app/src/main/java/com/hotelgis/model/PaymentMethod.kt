package com.hotelgis.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PaymentMethod(
    var type:Int,
    var method:String?,
    var image: String?,
    var title:String?
) : Parcelable
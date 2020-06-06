package com.hotelgis.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hotelgis.R
import com.hotelgis.model.Hotel
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class ListHotelAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<ListHotelAdapter.ViewHolder>() {

    var listHotel: List<Hotel> = arrayListOf()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_hotel, parent, false))
    }

    override fun getItemCount(): Int {
        return listHotel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel = listHotel[position]

//        Log.d("", hotel.toString())
        holder.tvHotelName.text = hotel.name
        holder.tvHotelAddress.text = hotel.address
        holder.tvHotelPhone.text = hotel.phone

        Glide.with(holder.itemView.context)
            .load(hotel.image)
            .error(R.drawable.ic_launcher_background)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.LEFT)))
            .into(holder.imgHotel)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listHotel[holder.adapterPosition]) }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvHotelName: TextView = itemView.findViewById(R.id.tvHotelName)
        var tvHotelAddress: TextView = itemView.findViewById(R.id.tvHotelAddress)
        var tvHotelPhone: TextView = itemView.findViewById(R.id.tvHotelPhone)
        var imgHotel: ImageView = itemView.findViewById(R.id.imgHotel)
    }

    interface OnItemClickCallback {
        fun onItemClicked(hotel: Hotel)
    }

}
package com.hotelgis.admin.adapter

import android.util.Log
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

class ListHotelAdapter(private val listViewType: List<Int>): RecyclerView.Adapter<ListHotelAdapter.ViewHolder>() {

    var listHotel: List<Hotel> = arrayListOf()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    companion object {
        val TYPE_HEADER = 0;
        val TYPE_CONTENT = 1;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(inflater.inflate(R.layout.item_header_list, parent, false))
            else -> ContentViewHolder(inflater.inflate(R.layout.item_content_list_hotel, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return listHotel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewType = listViewType[position]
        val hotel = listHotel[position]
        when (viewType) {
            TYPE_HEADER -> {
                val headerViewHolder = holder as HeaderViewHolder
                headerViewHolder.tvTitle.text = hotel.name
            }
            else -> {
                val contentViewHolder: ContentViewHolder = holder as ContentViewHolder
                Log.d("", hotel.toString())
                contentViewHolder.tvHotelName.text = hotel.name
                contentViewHolder.tvHotelAddress.text = hotel.address
                contentViewHolder.tvHotelPhone.text = hotel.phone

                Glide.with(holder.itemView.context)
                    .load(hotel.image)
                    .error(R.drawable.ic_launcher_background)
                    .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.LEFT)))
                    .into(contentViewHolder.imgHotel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = listViewType[position]

    open inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    inner class HeaderViewHolder(itemView: View): ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tvListTitle)
    }

    inner class ContentViewHolder(itemView: View): ViewHolder(itemView) {
        var tvHotelName: TextView = itemView.findViewById(R.id.tvHotelName)
        var tvHotelAddress: TextView = itemView.findViewById(R.id.tvHotelAddress)
        var tvHotelPhone: TextView = itemView.findViewById(R.id.tvHotelPhone)
        var imgHotel: ImageView = itemView.findViewById(R.id.imgHotel)
    }

}
package com.hotelgis.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hotelgis.R
import com.hotelgis.model.Room
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class ListRoomAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<ListRoomAdapter.ViewHolder>() {

    var listRoom: List<Room> = arrayListOf()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_room, parent, false))
    }

    override fun getItemCount(): Int {
        return listRoom.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room = listRoom[position]

        holder.tvRoomCode.text = " ${room.code}"
        holder.tvRoomName.text = room.name
        holder.tvRoomQuantity.text = room.quantity.toString()
        holder.tvRoomCost.text = "Rp. ${room.cost} / malam"

        holder.btnEditRoom.setOnClickListener {
            onItemClickCallback.onItemClicked(listRoom[holder.adapterPosition])
        }

        Glide.with(holder.itemView.context)
            .load(room.image)
            .error(R.drawable.ic_launcher_background)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.LEFT)))
            .into(holder.imgRoom)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvRoomCode: TextView = itemView.findViewById(R.id.tvRoomCode)
        var tvRoomName: TextView = itemView.findViewById(R.id.tvRoomName)
        var tvRoomQuantity: TextView = itemView.findViewById(R.id.tvRoomQuantity)
        var tvRoomCost: TextView = itemView.findViewById(R.id.tvRoomCost)
        var imgRoom: ImageView = itemView.findViewById(R.id.imgRoom)
        var btnEditRoom: Button = itemView.findViewById(R.id.btnEditRoom)
    }

    interface OnItemClickCallback {
        fun onItemClicked(room: Room)
    }
}
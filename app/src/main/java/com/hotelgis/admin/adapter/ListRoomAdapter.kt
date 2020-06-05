package com.roomgis.admin.adapter

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
import com.hotelgis.model.Room
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class ListRoomAdapter (private val listViewType: List<Int>): RecyclerView.Adapter<ListRoomAdapter.ViewHolder>() {

    var listRoom: List<Room> = arrayListOf()

    internal fun setRooms(notes : List<Room>){
        this.listRoom = notes
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
            else -> ContentViewHolder(inflater.inflate(R.layout.item_content_list_room, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return listRoom.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewType = listViewType[position]
        val room = listRoom[position]
        when (viewType) {
            TYPE_HEADER -> {
                val headerViewHolder = holder as HeaderViewHolder
                headerViewHolder.tvTitle.text = room.name
            }
            else -> {
                val contentViewHolder: ContentViewHolder = holder as ContentViewHolder
                Log.d("", room.toString())
                contentViewHolder.tvRoomCode.text = room.code
                contentViewHolder.tvRoomName.text = room.name
                contentViewHolder.tvRoomQuantity.text = room.quantity.toString()
                contentViewHolder.tvRoomCost.text = room.cost.toString()

                Glide.with(holder.itemView.context)
                    .load(room.image)
                    .error(R.drawable.ic_launcher_background)
                    .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.LEFT)))
                    .into(contentViewHolder.imgRoom)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = listViewType[position]

    open inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    inner class HeaderViewHolder(itemView: View): ViewHolder(itemView) {
        var tvTitle = itemView.findViewById<TextView>(R.id.tvListTitle)
    }

    inner class ContentViewHolder(itemView: View): ViewHolder(itemView) {
        var tvRoomCode: TextView = itemView.findViewById(R.id.tvRoomCode)
        var tvRoomName: TextView = itemView.findViewById(R.id.tvRoomName)
        var tvRoomQuantity: TextView = itemView.findViewById(R.id.tvRoomQuantity)
        var tvRoomCost: TextView = itemView.findViewById(R.id.tvRoomCost)
        var imgRoom: ImageView = itemView.findViewById(R.id.imgRoom)
    }
}
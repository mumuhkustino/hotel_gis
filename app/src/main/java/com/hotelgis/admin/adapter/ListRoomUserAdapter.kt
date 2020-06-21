package com.hotelgis.admin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hotelgis.LoginActivity
import com.hotelgis.PesanKamarActivity
import com.hotelgis.R
import com.hotelgis.model.Room
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class ListRoomUserAdapter : RecyclerView.Adapter<ListRoomUserAdapter.ViewHolder>() {
    private lateinit var context: Context
    var listRoom: List<Room> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_room_user, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listRoom.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room = listRoom[position]

        holder.tvRoomCode.text = room.code
        holder.tvRoomName.text = room.name
        holder.tvRoomQuantity.text = room.quantity.toString()
        holder.tvRoomCost.text = room.cost.toString()

        holder.btnPesanKamar.setOnClickListener {
            val intent = Intent(context, PesanKamarActivity::class.java).apply {
                putExtra("ROOM_CODE", room.code)
                putExtra("ROOM_NAME", room.name)
                putExtra("ROOM_COST", room.cost)
            }
            context.startActivity(intent)
        }

        holder.btnLocation.setOnClickListener {

        }

        Glide.with(holder.itemView.context)
            .load(room.image)
            .error(R.drawable.ic_launcher_background)
            .apply(
                RequestOptions.bitmapTransform(
                    RoundedCornersTransformation(
                        20,
                        0,
                        RoundedCornersTransformation.CornerType.LEFT
                    )
                )
            )
            .into(holder.imgRoom)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvRoomCode: TextView = itemView.findViewById(R.id.tvRoomCode)
        var tvRoomName: TextView = itemView.findViewById(R.id.tvRoomName)
        var tvRoomQuantity: TextView = itemView.findViewById(R.id.tvRoomQuantity)
        var tvRoomCost: TextView = itemView.findViewById(R.id.tvRoomCost)
        var imgRoom: ImageView = itemView.findViewById(R.id.imgRoom)
        var btnPesanKamar: Button = itemView.findViewById(R.id.btnPesanKamar)
        var btnLocation: ImageButton = itemView.findViewById(R.id.imgBtnLocation)
    }
}
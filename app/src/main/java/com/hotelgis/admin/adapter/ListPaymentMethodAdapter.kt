package com.hotelgis.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hotelgis.R
import com.hotelgis.model.PaymentMethod

class ListPaymentMethodAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<ListPaymentMethodAdapter.ViewHolder>() {

    companion object {
        val HEADER = 1
        val CONTENT = 2
    }

    var listMethodPayment: List<PaymentMethod> = arrayListOf()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListPaymentMethodAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER ->
                ViewHolderHeader(inflater.inflate(R.layout.item_header_payment_method, null))
            else ->
                ViewHolderContent(inflater.inflate(R.layout.item_content_payment_method, null))
        }
    }

    override fun getItemCount(): Int = listMethodPayment.size

    override fun onBindViewHolder(holder: ListPaymentMethodAdapter.ViewHolder, position: Int) {
        val viewType = listMethodPayment[position].type
        when (viewType) {
            HEADER -> {
                val viewHolderHeader = holder as ViewHolderHeader
                viewHolderHeader.tvPaymentMethod.text = listMethodPayment[position].method
            }
            else -> {
                val viewHolderContent = holder as ViewHolderContent
                Glide.with(holder.itemView.context)
                    .load(listMethodPayment[position].image)
                    .error(R.drawable.ic_launcher_background)
                    .into(viewHolderContent.imgPaymentMethod)
                viewHolderContent.tvPaymentMethod.text = listMethodPayment[position].title
            }
        }

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMethodPayment[holder.adapterPosition]) }
    }

    override fun getItemViewType(position: Int): Int = listMethodPayment[position].type

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ViewHolderHeader(itemView: View) : ViewHolder(itemView) {
        val tvPaymentMethod: TextView = itemView.findViewById(R.id.tvPaymentMethod)
    }

    inner class ViewHolderContent(itemView: View) : ViewHolder(itemView) {
        val imgPaymentMethod: ImageView = itemView.findViewById(R.id.imgPaymentMethod)
        val tvPaymentMethod: TextView = itemView.findViewById(R.id.tvPaymentMethod)
    }

    interface OnItemClickCallback {
        fun onItemClicked(paymentMethod: PaymentMethod)
    }
}
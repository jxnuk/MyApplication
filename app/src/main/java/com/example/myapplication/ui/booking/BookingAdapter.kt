package com.example.myapplication.ui.booking

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.database.data.Booking
import com.google.android.material.button.MaterialButton

class BookingAdapter(
    private var items: List<Booking>,
    private val onCancel: (Booking) -> Unit
) : RecyclerView.Adapter<BookingAdapter.VH>() {

    fun submit(list: List<Booking>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_booking, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val b = items[position]
        h.tvNumber.text = "Booking number - ${b.bookingNumber}"
        h.tvDetails.text = "${b.userName}\n${b.dateTimeText}"
        h.tvPhone.text = b.phone
        h.img.setImageResource(h.img.context.safeDrawableId(b.imageResName))
        h.btnCancel.setOnClickListener { onCancel(b) }
    }

    override fun getItemCount() = items.size

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgRestaurant)
        val tvNumber: TextView = v.findViewById(R.id.tvNumber)
        val tvDetails: TextView = v.findViewById(R.id.tvDetails)
        val tvPhone: TextView = v.findViewById(R.id.tvPhone)
        val btnCancel: MaterialButton = v.findViewById(R.id.btnCancel)
    }
}

// Helper to map stored drawable name → id (falls back to a placeholder)
private fun Context.safeDrawableId(name: String): Int {
    val id = resources.getIdentifier(name, "drawable", packageName)
    return if (id != 0) id else R.drawable.special
}

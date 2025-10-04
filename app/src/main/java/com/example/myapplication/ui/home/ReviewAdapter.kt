package com.example.myapplication.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ReviewAdapter(
    private val items: List<Review>,
    private val onHelpful: (Review, Boolean) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitle: TextView = v.findViewById(R.id.tvTitle)
        val tvBody: TextView = v.findViewById(R.id.tvBody)
        val tvAuthor: TextView = v.findViewById(R.id.tvAuthor)
        val imgThumb: ImageView = v.findViewById(R.id.imgThumb)
        val btnYes: ImageButton = v.findViewById(R.id.btnYes)
        val btnNo: ImageButton = v.findViewById(R.id.btnNo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val item = items[position]
        h.tvTitle.text = item.title
        h.tvBody.text = item.body
        h.tvAuthor.text = "Author: ${item.author}"
        if (item.imageRes != null) {
            h.imgThumb.visibility = View.VISIBLE
            h.imgThumb.setImageResource(item.imageRes)
        } else {
            h.imgThumb.visibility = View.GONE
        }
        h.btnYes.setOnClickListener { onHelpful(item, true) }
        h.btnNo.setOnClickListener { onHelpful(item, false) }
    }

    override fun getItemCount() = items.size
}

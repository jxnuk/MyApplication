package com.example.myapplication.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

private const val LEFT = 0
private const val RIGHT = 1

class CommunityPostAdapter(
    private val items: List<CommunityPost>,
    private val onAction: (CommunityPost, String) -> Unit
) : RecyclerView.Adapter<CommunityPostAdapter.VH>() {

    override fun getItemViewType(position: Int) = if (position % 2 == 0) LEFT else RIGHT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layout = if (viewType == LEFT) R.layout.item_post_left else R.layout.item_post_right
        val v = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        val holder = VH(v)
        v.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) onAction(items[pos], "open")
        }
        return holder
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val item = items[position]
        h.body.text = item.body
        h.img.setImageResource(item.imageRes)

        h.close.setOnClickListener { onAction(item, "close") }
        h.share.setOnClickListener { onAction(item, "share") }
        h.location.setOnClickListener { onAction(item, "location") }
        h.comment.setOnClickListener { onAction(item, "comment") }
    }

    override fun getItemCount() = items.size

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.img)
        val body: TextView = v.findViewById(R.id.tvBody)
        val close: ImageButton = v.findViewById(R.id.btnClose)
        val share: ImageButton = v.findViewById(R.id.btnShare)
        val location: ImageButton = v.findViewById(R.id.btnLocation)
        val comment: ImageButton = v.findViewById(R.id.btnComment)
    }
}

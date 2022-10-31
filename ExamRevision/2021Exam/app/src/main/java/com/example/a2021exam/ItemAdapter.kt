package com.example.a2021exam

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private var items: List<String>, private val onDeleteButtonListener: OnDeleteButtonListener) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(view: View, val onDeleteButtonListener: OnDeleteButtonListener):
        RecyclerView.ViewHolder(view) {
        val textView: TextView
        val deleteButton: ImageButton
        val editButton: ImageButton

        init {
            textView = view.findViewById(R.id.item_text)
            editButton = view.findViewById(R.id.edit_button)
            deleteButton = view.findViewById(R.id.delete_button)
            deleteButton.setOnClickListener { onDeleteButtonListener.onDeleteClick(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(view, onDeleteButtonListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position]
    }

    override fun getItemCount() = items.size

    fun setData(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }

    interface OnDeleteButtonListener {
        fun onDeleteClick(position: Int)
    }
}
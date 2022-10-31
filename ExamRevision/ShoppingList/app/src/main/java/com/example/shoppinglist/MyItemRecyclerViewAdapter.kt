package com.example.shoppinglist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.shoppinglist.databinding.FragmentListBinding


class MyItemRecyclerViewAdapter(
    private var values: List<ShoppingItem>,
    private val onDeleteButtonListener: OnDeleteButtonListener,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onDeleteButtonListener,
            onItemClickListener
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.toString()
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(binding: FragmentListBinding, private val onDeleteButtonListener: OnDeleteButtonListener,
    private val onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        val contentView: TextView
        val deleteButton: ImageButton

        init {
            contentView = binding.itemNameText
            deleteButton = binding.deleteButton
            deleteButton.setOnClickListener{
                 onDeleteButtonListener.onDeleteClick(bindingAdapterPosition)
            }
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(bindingAdapterPosition)
        }
    }

    fun setData(newItems: List<ShoppingItem>) {
        values = newItems
        notifyDataSetChanged()
    }

    interface OnDeleteButtonListener {
        fun onDeleteClick(position: Int)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}
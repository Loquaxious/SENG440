package com.example.livedata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FriendAdapter(private var friends: List<Friend>, private val onFriendListener: OnFriendListener,
private val onButtonListener: OnButtonListener) :
    RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    class FriendViewHolder(view: View, val onFriendListener: OnFriendListener,
                           val onButtonListener: OnButtonListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        val textView: TextView
        val menuButton: ImageButton

        init {
            textView = view.findViewById(R.id.friendText)
            menuButton = view.findViewById(R.id.menuButton)
            menuButton.setOnClickListener {
                onButtonListener.onButtonClick(bindingAdapterPosition)
            }
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onFriendListener.onFriendClick(bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_item, parent, false)

        return FriendViewHolder(view, onFriendListener, onButtonListener)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.textView.text = friends[position].toString()
    }

    override fun getItemCount() = friends.size

    fun setData(newFriends: List<Friend>) {
        friends = newFriends
        notifyDataSetChanged()
    }

    interface OnFriendListener {
        fun onFriendClick(position: Int)
    }

    interface OnButtonListener {
        fun onButtonClick(position: Int)
    }
}
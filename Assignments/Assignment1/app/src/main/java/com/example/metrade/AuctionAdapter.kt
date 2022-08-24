package com.example.metrade

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AuctionAdapter(private var auctions: List<ListAuction>, private val onAuctionListener: OnAuctionListener)
    : RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder>() {

    class AuctionViewHolder(itemView: View, val onAuctionListener: OnAuctionListener)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView
        val currentBid: TextView
        val auctionImage: ImageView

        init {
            title = itemView.findViewById(R.id.auction_title)
            currentBid = itemView.findViewById(R.id.auction_bid)
            auctionImage = itemView.findViewById(R.id.auction_image)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onAuctionListener.onAuctionClick(adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuctionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_auction, parent, false)
        return AuctionViewHolder(view, onAuctionListener)
    }

    override fun onBindViewHolder(viewHolder: AuctionViewHolder, position: Int) {
        viewHolder.title.text = auctions[position].title
        viewHolder.currentBid.text = "$${auctions[position].highestBid}"
        Picasso.get().load(BASE_URL + "auctions/${auctions[position].auctionId}/image").into(viewHolder.auctionImage)
    }

    override fun getItemCount() = auctions.size

    fun setAuctions(newAuctions: List<ListAuction>) {
        auctions = newAuctions
        notifyDataSetChanged()
    }

    interface OnAuctionListener {
        fun onAuctionClick(position: Int)
    }
}
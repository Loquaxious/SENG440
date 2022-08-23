package com.example.metrade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.net.URL

const val BASE_URL = "https://seng365.csse.canterbury.ac.nz/api/v1/"

class LandingActivity : AppCompatActivity(), AuctionAdapter.OnAuctionListener {
    private var auctions : List<ListAuction> = listOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var auctionAdapter: AuctionAdapter
//    private lateinit var sortButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAuctions()

//        sortButton = findViewById(R.id.sortButton)
//        sortButton.setOnClickListener {
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Sort/Filter Auctions")
//
//        }

        auctionAdapter = AuctionAdapter(auctions, this)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = auctionAdapter
    }


    private fun getAuctions() {
        lifecycleScope.launch {
            val result = getJson(URL(BASE_URL + "auctions"))
            val jsonAuctions = result.getJSONArray("auctions")
            auctions = (0 until jsonAuctions.length()).map { i ->
                val auction = jsonAuctions.getJSONObject(i)
                ListAuction(auction.getInt("auctionId"), auction.getString("title"),
                    auction.getString("endDate"), auction.getInt("categoryId"), auction.optInt("reserve", 0),
                    auction.getInt("sellerId"), auction.getString("sellerFirstName"),
                    auction.getString("sellerLastName"), auction.optInt("numBids", 0),
                    auction.optInt("highestBid", 0))
            }
            auctionAdapter.setAuctions(auctions)
        }
    }


    override fun onAuctionClick(position: Int) {
        val intent = Intent(this, AuctionActivity::class.java)
        intent.putExtra("auctionId", auctions[position].auctionId)
        startActivity(intent)
    }

}
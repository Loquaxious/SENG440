package com.example.metrade

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.net.URL

const val BASE_URL = "https://seng365.csse.canterbury.ac.nz/api/v1/"
const val SORT_ACTIVITY = 1628420694

class LandingActivity : AppCompatActivity(), AuctionAdapter.OnAuctionListener {
    private var auctions : List<ListAuction> = listOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var auctionAdapter: AuctionAdapter
    private lateinit var sortButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAuctions(URL(BASE_URL + "auctions"))

        sortButton = findViewById(R.id.sortButton)
        sortButton.setOnClickListener {
            val intent = Intent(this, SortFilterActivity::class.java)
            startActivityForResult(intent, SORT_ACTIVITY)
        }

        auctionAdapter = AuctionAdapter(auctions, this)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = auctionAdapter

    }


    private fun getAuctions(url: URL) {
        lifecycleScope.launch {
            val result = getJson(url)
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
        val options = ActivityOptions
            .makeSceneTransitionAnimation(this)
        startActivity(intent, options.toBundle())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SORT_ACTIVITY && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                filterAuctions(data.getStringExtra("query").toString(),
                    data.getIntExtra("category", 0),
                    data.getIntExtra("sort", 0))
            }
            Toast.makeText(this, getString(R.string.toast_filtered_message), Toast.LENGTH_SHORT).show()
        }
    }

    private fun filterAuctions(query: String, categoryPos: Int, sortPos: Int) {
        val sortBy = mapOf(0 to "ALPHABETICAL_ASC", 1 to "ALPHABETICAL_DESC", 2 to "BIDS_ASC",
            3 to "BIDS_DESC", 4 to "RESERVE_ASC", 5 to "RESERVE_DESC", 6 to "CLOSING_LAST", 7 to "CLOSING_SOON")
        if (query.isNullOrEmpty()) {
            if (categoryPos == 0) {
                getAuctions(URL(BASE_URL + "auctions?sortBy=${sortBy[sortPos]}"))
            } else {
                getAuctions(URL(BASE_URL + "auctions?categoryIds=${categoryPos}&sortBy=${sortBy[sortPos]}"))
            }
        } else if (categoryPos ==  0) {
            getAuctions(URL(BASE_URL + "auctions?q=${query}&sortBy=${sortBy[sortPos]}"))
        } else {
            getAuctions(URL(BASE_URL + "auctions?q=${query}&categoryIds=${categoryPos}&sortBy=${sortBy[sortPos]}"))
        }
    }


}
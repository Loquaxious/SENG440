package com.example.metrade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.net.URL

const val BASE_URL = "https://seng365.csse.canterbury.ac.nz/api/v1/"

class LandingActivity : AppCompatActivity() {
    private var auctions : List<Auction> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)


        fun getAuctions() {
            lifecycleScope.launch {
                val result = getJson(URL(BASE_URL + "auctions"))
                val jsonAuctions = result.getJSONObject("response").getJSONArray("auctions")
                auctions = (0 until jsonAuctions.length()).map { i ->
                    val auction = jsonAuctions.getJSONObject(i)
                    Auction(auction.getInt("auctionId"), auction.getString("title"),
                        auction.getString("description"), auction.getString("endDate"),
                        auction.getInt("categoryId"), auction.getInt("reserve"),
                        auction.getInt("sellerId"), auction.getString("sellerFirstName"),
                        auction.getString("sellerLastName"), auction.getInt("numBids"),
                        auction.getInt("highestBid"))
                }

            }
        }

    }
}
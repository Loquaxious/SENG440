package com.example.metrade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AuctionActivity() : AppCompatActivity() {
    private var auctionId = 0
    private lateinit var auction: Auction
    private lateinit var auctionImage: ImageView
    private lateinit var auctionTitle: TextView
    private lateinit var auctionDescription: TextView
    private lateinit var auctionEndDate: TextView
    private lateinit var auctionReserve: TextView
    private lateinit var sellerName: TextView
    private lateinit var auctionNumBids: TextView
    private lateinit var auctionCurrentBid: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auction)

        auctionId = intent.getIntExtra("auctionId", 0)!!

        getAuction()

        auctionImage = findViewById(R.id.auctionImage)
        auctionTitle = findViewById(R.id.auctionTitle)
        auctionDescription = findViewById(R.id.auctionDescription)
        auctionEndDate = findViewById(R.id.auctionEndDate)
        auctionReserve = findViewById(R.id.auctionReserve)
        auctionNumBids = findViewById(R.id.auctionNumBids)
        auctionCurrentBid = findViewById(R.id.auctionCurrentBid)
        sellerName = findViewById(R.id.sellerName)

        findViewById<TextView>(R.id.header_endDate).text = R.string.endDate_header.toString()
        findViewById<TextView>(R.id.header_currentBid).text = R.string.currentBid_header.toString()
        findViewById<TextView>(R.id.header_reserve).text = R.string.reserve_header.toString()
        findViewById<TextView>(R.id.header_numBids).text = R.string.numBids_header.toString()
        findViewById<TextView>(R.id.header_description).text = R.string.description_header.toString()
        findViewById<TextView>(R.id.header_seller).text = R.string.seller_header.toString()
        findViewById<Button>(R.id.addCalendar).text = R.string.button_addCalendar.toString()

        // TODO Button listener and intent for calendar
    }

    private fun getAuction() {
        lifecycleScope.launch {
            val result = getJson(URL(BASE_URL + "auctions/${auctionId}"))
            auction = Auction(
                result.getInt("auctionId"), result.getString("title"),
                result.getString("description"), result.getString("endDate"),
                result.getInt("categoryId"), result.optInt("reserve", 0),
                result.getInt("sellerId"), result.getString("sellerFirstName"),
                result.getString("sellerLastName"), result.optInt("numBids", 0),
                result.optInt("highestBid", 0)
            )
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm a", Locale.getDefault())

            val inputDate = inputFormat.parse(auction.endDate)
            val endDateTime = outputFormat.format(inputDate)

            Picasso.get().load(BASE_URL + "auctions/${auctionId}/image").into(auctionImage)
            auctionTitle.text = auction.title
            auctionDescription.text = auction.description
            auctionEndDate.text = endDateTime.toString()
            auctionReserve.text = "$" + auction.reserve
            auctionNumBids.text = auction.numBids.toString()
            auctionCurrentBid.text = "$" + auction.highestBid
            sellerName.text = auction.sellerFirstName + " " + auction.sellerLastName
        }
    }
}
package com.example.metrade

data class Auction (
    val auctionId: Int,
    val title: String,
    val description: String,
    val endDate: String,
    val categoryId: Int,
    val reserve: Int,
    val sellerId: Int,
    val sellerFirstName: String,
    val sellerLastName: String,
    val numBids: Int,
    val highestBid: Int,
)
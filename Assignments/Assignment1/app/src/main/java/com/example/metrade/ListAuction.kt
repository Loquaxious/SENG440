package com.example.metrade


data class ListAuction (
    val auctionId: Int,
    val title: String,
    val endDate: String,
    val categoryId: Int,
    val reserve: Int,
    val sellerId: Int,
    val sellerFirstName: String,
    val sellerLastName: String,
    val numBids: Int,
    val highestBid: Int,
        )
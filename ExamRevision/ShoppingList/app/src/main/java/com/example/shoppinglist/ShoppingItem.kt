package com.example.shoppinglist

class ShoppingItem (
    val name : String,
    val description: String
        ) {
    override fun toString(): String {
        return this.name
    }
}
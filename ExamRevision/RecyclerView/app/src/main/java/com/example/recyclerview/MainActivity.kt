package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), FriendsAdapter.OnFriendListener {
    private val friends = arrayOf(
        Friend(
            "Ben Adams",
            "U8U061XEY",
            "Christchurch, New Zealand",
            "benjamin.adams@canterbury.ac.nz",
            "123456789"
        ),
        Friend(
            "Homer Simpson",
            "U80000000",
            "Springfield, USA",
            "homer.simpson@example.org",
            "987654321"
        ),
        Friend(
            "Marge Simpson",
            "U70000000",
            "Springfield, USA",
            "marge.simpson@example.org",
            "987654320"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val friendsAdapter = FriendsAdapter(friends, this)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = friendsAdapter
    }

    override fun onFriendClick(position: Int) {
        val options = arrayOf("Map", "Email", "Text", "Call", "Slack")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Connect how?")
        builder.setItems(options) {_, optionId ->
            dispatchAction(optionId, friends[position])
        }
        builder.show()
    }

    private fun dispatchAction(optionId: Int, friend: Friend) {
        when (optionId) {
            0 -> TODO("MAP")
            1 -> TODO("EMAIL")
            2 -> TODO("TEXT")
            3 -> TODO("Call")
            4 -> TODO("Slack")
        }
    }
}
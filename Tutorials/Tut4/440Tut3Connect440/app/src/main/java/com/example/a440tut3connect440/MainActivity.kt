package com.example.a440tut3connect440

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import java.net.URLEncoder

class Friend (val name: String,
val slackId: String,
val home: String,
val email: String,
val phone: String) {
    override fun toString() = name
}

class MainActivity : AppCompatActivity(), FriendAdapter.OnFriendListener {
    private val friends = arrayOf<Friend>(
        Friend("Ben Adams", "bta47", "Christchurch, NZ", "benjamin.adams@canterbury.ac.nz", "#######"),
        Friend("Logan Lee", "lpl29", "Hawaii, USA", "123@gmail.com", "12345678"),
        Friend("Bailea Twomey", "Baileaa", "Killorglin, Ireland", "baileaa@gmail.com", "12345678"),
        Friend("Friend 1", "p1", "Christchurch, NZ", "p.1@canterbury.ac.nz", "#######"),
        Friend("Friend 2", "p2", "Christchurch, NZ", "p.2@canterbury.ac.nz", "#######"),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)
        val friendAdapter = FriendAdapter(friends, this)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = friendAdapter
    }

    override fun onFriendClick(position: Int) {
        val options = arrayOf("Map", "Email", "Text", "Call", "Stack")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Connect how?")
        builder.setItems(options) { _, optionId ->
            dispatchAction(optionId, friends[position])
        }
        builder.show()
    }

    fun dispatchAction(optionId: Int, friend: Friend) {
        when (optionId) {
            0 -> {
                val uri = Uri.parse("geo:0,0?q=${URLEncoder.encode(friend.home, "UTF-8")}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            1 -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_EMAIL, friend.email)
                startActivity(intent)
            }
            2 -> {
                val uri = Uri.parse("smsto:${friend.phone}")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                startActivity(intent)
            }
            3 -> {
                val uri = Uri.parse("tel:${friend.phone}")
                val intent = Intent(Intent.ACTION_DIAL, uri)
                startActivity(intent)

            }
//            4 -> {
//                val uri = Uri.parse("slack://user?team=$teamId&id=${friend.slackId}")
//                val intent = Intent(Intent.ACTION_VIEW, uri)
//                startActivity(intent)
//
//            }
        }
    }

}
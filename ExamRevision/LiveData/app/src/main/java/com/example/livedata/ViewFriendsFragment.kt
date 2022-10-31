package com.example.livedata

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import java.net.URLEncoder


class ViewFriendsFragment : Fragment(), FriendAdapter.OnFriendListener, FriendAdapter.OnButtonListener {
    private val viewModel: FriendsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_friends, container, false)

        val friendAdapter = FriendAdapter(viewModel.friends.value!!, this, this)
        viewModel.friends.observe(viewLifecycleOwner) { newFriends ->
            friendAdapter.setData(newFriends)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = friendAdapter

        return view
    }

    override fun onFriendClick(position: Int) {
        val options = arrayOf("Map", "Email", "Text", "Call", "Slack")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Connect how?")
        builder.setItems(options) {_, optionId ->
            dispatchAction(optionId, viewModel.friends.value!![position])
        }
     builder.show()
    }

    override fun onButtonClick(position: Int) {
        val options = arrayOf("Edit", "Delete")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Edit or Delete?")
        builder.setItems(options) { _, optionId ->
            dispatchMenuAction(optionId, position, viewModel.friends.value!![position])
        }
        builder.show()
    }

    private fun dispatchAction(optionId: Int, friend: Friend) {
     when(optionId) {
         0 -> {
             val uri = Uri.parse("geo:0,0?q=${URLEncoder.encode(friend.home, "UTF-8")}")
             val intent = Intent(Intent.ACTION_SEND, uri)
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
             //val intent = Intent(Intent.ACTION_CALL, uri)
             startActivity(intent)
         }
         4 -> {
             val uri = Uri.parse("slack://user?team=TR8N4694&id=${friend.slackId}")
             val intent = Intent(Intent.ACTION_VIEW, uri)
             startActivity(intent)
         }
     }
    }

    private fun dispatchMenuAction(optionId: Int, position: Int, friend: Friend) {
        when(optionId) {
            0 -> {
                editFriend(position, friend)
            }
            1 -> {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Are you sure you want to delete this friend?")
                    .setNegativeButton("Delete") {_,_ ->
                        viewModel.deleteFriend(position)
                    }
                builder.show()
            }
        }
    }

    private fun editFriend(position: Int, friend: Friend) {
        val builder = AlertDialog.Builder(requireContext())

        val form = layoutInflater.inflate(R.layout.dialog_edit_friend, null, false)
        builder.setView(form)

        val nameBox: EditText = form.findViewById(R.id.editNameBox)
        nameBox.setText(friend.name)
        val slackBox: EditText = form.findViewById(R.id.editSlackBox)
        slackBox.setText(friend.slackId)
        val homeBox: EditText = form.findViewById(R.id.editHomeBox)
        homeBox.setText(friend.home)
        val phoneBox: EditText = form.findViewById(R.id.editPhoneBox)
        phoneBox.setText(friend.phone)
        val emailBox: EditText = form.findViewById(R.id.editEmailBox)
        emailBox.setText(friend.email)

        builder.setPositiveButton("Save") {_, _ ->
            val editedFriend = Friend(
                nameBox.text.toString(),
                slackBox.text.toString(),
                homeBox.text.toString(),
                phoneBox.text.toString(),
                emailBox.text.toString()
            )
            viewModel.editFriend(position, editedFriend)
        }
        builder.show()
    }
}
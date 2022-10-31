package com.example.livedata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private val viewModel: FriendsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel.numFriends.observe(viewLifecycleOwner) { num ->
            val connectedFriendsText: TextView = view.findViewById(R.id.connectedFriendsText)
            connectedFriendsText.text = "You have connected with ${num} friends"
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.friendListButton).setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_viewFriendsFragment, null)
        )

        view.findViewById<Button>(R.id.addFriendButton).setOnClickListener {
            addNewFriend()
        }
    }


    private fun addNewFriend() {
        val builder = AlertDialog.Builder(requireContext())

        val form = layoutInflater.inflate(R.layout.dialog_add_friend, null, false)
        builder.setView(form)

        val nameBox: EditText = form.findViewById(R.id.addNameBox)
        val slackBox: EditText = form.findViewById(R.id.addSlackBox)
        val homeBox: EditText = form.findViewById(R.id.addHomeBox)
        val phoneBox: EditText = form.findViewById(R.id.addPhoneBox)
        val emailBox: EditText = form.findViewById(R.id.addEmailBox)

        builder.setPositiveButton("Add") {_, _ ->
            val newFriend = Friend(
                nameBox.text.toString(),
                slackBox.text.toString(),
                homeBox.text.toString(),
                phoneBox.text.toString(),
                emailBox.text.toString()
            )
            viewModel.addFriend(newFriend)
        }

        builder.show()
    }



}
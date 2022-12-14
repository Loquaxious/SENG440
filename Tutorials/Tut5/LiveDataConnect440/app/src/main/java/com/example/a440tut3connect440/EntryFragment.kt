package com.example.a440tut3connect440

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels


/**
 * A simple [Fragment] subclass.
 * Use the [EntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EntryFragment : Fragment() {

    private val viewModel: FriendsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_entry, container, false)

        viewModel.numFriends.observe(viewLifecycleOwner, { num ->
            val welcomeTextView: TextView = view.findViewById(R.id.welcomeTextView)
            welcomeTextView.text = "You have connected with ${num} friends"
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.buttonAddFriend)?.setOnClickListener {
            addNewFriend()
        }

    }


    private fun addNewFriend() {
        val builder = AlertDialog.Builder(context)

        val form = layoutInflater.inflate(R.layout.dialog_add_friend, null, false)
        builder.setView(form)

        val nameBox: EditText = form.findViewById(R.id.nameBox)
        val slackBox: EditText = form.findViewById(R.id.slackBox)
        val homeBox: EditText = form.findViewById(R.id.homeBox)
        val phoneBox: EditText = form.findViewById(R.id.phoneBox)
        val emailBox: EditText = form.findViewById(R.id.emailBox)

        builder.setPositiveButton("Add") { _, _ ->
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
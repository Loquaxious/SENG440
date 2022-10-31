package com.example.shoppinglist

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

class HomeFragment : Fragment() {

    private val viewModel: ItemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val title = view.findViewById<TextView>(R.id.home_title_text)
        viewModel.numItems.observe(viewLifecycleOwner) {num ->
            title.text = "Your shopping list has ${num} items"
        }


        val listButton = view.findViewById<Button>(R.id.show_list_button)
        listButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_listFragment))

        val addButton = view.findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())

            builder.setTitle("Add new Shopping list item")
            val form = layoutInflater.inflate(R.layout.add_item_dialog, null, false)
            builder.setView(form)

            builder.setPositiveButton("Add", ) {_, _ ->
                viewModel.addItem(
                    ShoppingItem(
                        form.findViewById<EditText>(R.id.nameBox).text.toString(),
                        form.findViewById<EditText>(R.id.descriptionBox).text.toString()
                    )
                )
            }
            builder.show()
        }

        val settingsButton = view.findViewById<Button>(R.id.settings_button)
        settingsButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_settingsFragment))

        return view
    }

}
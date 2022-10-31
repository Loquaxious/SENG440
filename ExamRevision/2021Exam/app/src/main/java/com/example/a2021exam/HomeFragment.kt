package com.example.a2021exam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController


class HomeFragment : Fragment() {
    private val viewModel: ItemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fromBox = view.findViewById<EditText>(R.id.from_box)
        val toBox = view.findViewById<EditText>(R.id.to_box)
        val itemBox = view.findViewById<EditText>(R.id.item_box)

        val clearButton = view.findViewById<Button>(R.id.clear_button)
        clearButton.setOnClickListener {
            itemBox.text.clear()
        }
        val startButton = view.findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            val stringItems = itemBox.text.toString()
            val listItems = stringItems.split('\n')
            viewModel.setItems(listItems.toMutableList())
            viewModel.setFromText(fromBox.text.toString())
            viewModel.setToText(toBox.text.toString())
            view.findNavController().navigate(R.id.action_homeFragment_to_listFragment)
        }
    }

}
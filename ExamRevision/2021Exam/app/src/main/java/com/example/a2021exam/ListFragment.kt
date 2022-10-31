package com.example.a2021exam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView


class ListFragment : Fragment(), ItemAdapter.OnDeleteButtonListener {
    private val viewModel: ItemViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val fromText = view.findViewById<TextView>(R.id.from_list_text)
        fromText.text = viewModel.fromText.value.toString()
        val toText = view.findViewById<TextView>(R.id.to_list_text)
        toText.text = viewModel.toText.value.toString()

        val itemAdapter = ItemAdapter(viewModel.items.value!!.toList(), this)
        viewModel.items.observe(viewLifecycleOwner) {newItems ->
            itemAdapter.setData(newItems)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = itemAdapter

        return view
    }


    override fun onDeleteClick(position: Int) {
        viewModel.deleteItem(position)
    }

}
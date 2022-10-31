package com.example.shoppinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels

/**
 * A fragment representing a list of Items.
 */
class ListFragment : Fragment(), MyItemRecyclerViewAdapter.OnDeleteButtonListener, MyItemRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: ItemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_list, container, false)

        val itemAdapter = MyItemRecyclerViewAdapter(viewModel.items.value!!.toList(), this, this)
        viewModel.items.observe(viewLifecycleOwner) {newItems ->
            itemAdapter.setData(newItems)
        }
        view.findViewById<RecyclerView>(R.id.list).adapter = itemAdapter

        return view
    }

    override fun onDeleteClick(position: Int) {
        viewModel.deleteItem(position)
    }

    override fun onItemClick(position: Int) {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Item Description")
        builder.setMessage(viewModel.items.value?.get(position)?.description)

        builder.show()
    }

}
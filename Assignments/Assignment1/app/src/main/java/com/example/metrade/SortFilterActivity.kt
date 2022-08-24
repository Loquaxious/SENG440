package com.example.metrade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import java.util.*

private lateinit var categories: Spinner
private lateinit var searchQuery: SearchView
private lateinit var sortBy: Spinner

private var selectedCategory = 0
private var selectedSortBy = 7

class SortFilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_filter)

        categories = findViewById(R.id.category_spinner)
        sortBy = findViewById(R.id.sortBy_spinner)
        searchQuery = findViewById(R.id.searchQuery)

        ArrayAdapter.createFromResource(this, R.array.array_categories,
            android.R.layout.simple_dropdown_item_1line
        ).also {
              adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            categories.adapter = adapter
        }

        ArrayAdapter.createFromResource(this, R.array.array_sort_options,
            android.R.layout.simple_dropdown_item_1line
        ).also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            sortBy.adapter = adapter
        }

        val categoryListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
               selectedCategory = pos
            }

        }

        val sortByListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                selectedSortBy = pos
            }
        }

        categories.setSelection(0)
        sortBy.setSelection(7)
    }
}
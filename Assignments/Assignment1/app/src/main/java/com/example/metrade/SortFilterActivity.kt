package com.example.metrade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import java.util.*

private lateinit var categories: Spinner
private lateinit var searchQuery: SearchView
private lateinit var sortBy: Spinner

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
    }
}
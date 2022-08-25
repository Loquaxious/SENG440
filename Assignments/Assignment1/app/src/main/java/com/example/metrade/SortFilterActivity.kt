package com.example.metrade

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.JsonWriter
import android.view.View
import android.widget.*
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

private lateinit var categories: Spinner
private lateinit var searchQuery: EditText
private lateinit var sortBy: Spinner
private lateinit var submitButton: Button

private var selectedCategory = 0
private var selectedSortBy = 7

class SortFilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_filter)

        categories = findViewById(R.id.category_spinner)
        sortBy = findViewById(R.id.sortBy_spinner)
        searchQuery = findViewById(R.id.searchQuery)
        submitButton = findViewById(R.id.submit_button)

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

        val categoryListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCategory = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCategory = 0
            }

        }

        val sortByListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedSortBy = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedSortBy = 7
            }
        }

        categories.onItemSelectedListener = categoryListener
        sortBy.onItemSelectedListener = sortByListener

        categories.setSelection(0)
        sortBy.setSelection(7)

        submitButton.setOnClickListener {
            val result = Intent()
            result.putExtra("query", searchQuery.text.toString())
            result.putExtra("category", selectedCategory)
            result.putExtra("sort", selectedSortBy)
            setResult(Activity.RESULT_OK, result)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        try{
            val file = openFileInput("sortState.json")
            val reader = JsonReader(InputStreamReader(file))
            reader.beginObject()
            while(reader.hasNext()) {
                val key = reader.nextName()
                when(key) {
                    "query" -> searchQuery.setText(reader.nextString())
                    "category" -> categories.setSelection(reader.nextInt())
                    "sortBy" -> sortBy.setSelection(reader.nextInt())
                }
            }
            reader.endObject()
            reader.close()
        } catch (e: FileNotFoundException) {
            println("FILE READ FAILURE")
            return
        }
    }

    override fun onStop() {
        super.onStop()
        val file = openFileOutput("sortState.json", Context.MODE_PRIVATE)
        val writer = JsonWriter(OutputStreamWriter(file))
        writer.setIndent("  ")
        write(writer)
        writer.close()
    }

    private fun write(writer: JsonWriter) {
        writer.beginObject()
        writer.name("query").value(searchQuery.text.toString())
        writer.name("category").value(selectedCategory)
        writer.name("sortBy").value(selectedSortBy)
        writer.endObject()
    }
}
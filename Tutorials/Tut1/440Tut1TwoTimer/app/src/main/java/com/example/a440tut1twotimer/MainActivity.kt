package com.example.a440tut1twotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var picker1: Spinner
    private lateinit var picker2: Spinner
    private lateinit var handler: Handler
    private lateinit var updateTask: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeZones = TimeZone.getAvailableIDs()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, timeZones)
        picker1.adapter = adapter
        picker2.adapter = adapter
        handler = Handler(Looper.getMainLooper())

        updateTask = Runnable {
            syncTimes()
            handler.postDelayed(updateTask, 1000)
        }

        val listener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                syncTimes()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                syncTimes()
            }
        }

        picker1.onItemSelectedListener = listener
        picker2.onItemSelectedListener = listener
    }

    override fun onStart() {
        super.onStart()
        handler.post(updateTask)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(updateTask)
    }

    private fun syncTimes() {
        val formatter = SimpleDateFormat("d MMMM HH:mm:ss")
        val today = Calendar.getInstance()

        var timeZone = TimeZone.getTimeZone(picker1.selectedItem.toString())
        formatter.timeZone = timeZone

        timeZone = TimeZone.getTimeZone(picker2.selectedItem.toString())
        formatter.timeZone = timeZone

    }
}
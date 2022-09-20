package nz.ac.canterbury.seng440.backlog

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.widget.TimePicker
import java.util.*

//
// Step 8 - add TimePickerDialog
//
class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    private val AlarmReceiver = AlarmReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //
        // Step 2
        //
        createNotificationChannel()

        //
        // Step 7
        //
        setReminderTime()
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(AlarmReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(AlarmReceiver)
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(Notification.CATEGORY_REMINDER, "Daily Reminders", importance).apply {
            description = "Send daily reminders to capture memories"
        }
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    //
    // Step 7
    //
    private fun setReminderTime() {
        val fragment = TimePickerFragment()
        fragment.listener = this
        fragment.show(supportFragmentManager, null)
    }


    override fun onTimeSet(picker: TimePicker, hour: Int, minute: Int) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.edit().apply {
            putInt("hour", hour)
            putInt("minute", minute)
            apply()
        }

        Utilities.scheduleReminder(applicationContext, hour, minute)
        val receiver = ComponentName(this, BootReceiver::class.java)
        packageManager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)

    }

}

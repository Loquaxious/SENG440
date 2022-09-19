package nz.ac.canterbury.seng440.backlog

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

fun Bundle.toParamsString() = keySet().map { "$it -> ${get(it)}" }.joinToString("\n")

class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("ServiceCast")
    override fun onReceive(context: Context, intent: Intent) {
        //
        // Step 1
        //
        Log.d("FOO", "Received message ${intent.action} with\n${intent.extras?.toParamsString()}")

        //
        // Step 3 & 4 & 5
        //
    }
}

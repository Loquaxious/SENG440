package nz.ac.canterbury.seng440.backlog

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager

//
// Step 10
//
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        if (prefs.getInt("hour", -1) >= 0) {
            Utilities.scheduleReminder(context, prefs.getInt("hour", 6), prefs.getInt("minute", 0))
        }
    }

}

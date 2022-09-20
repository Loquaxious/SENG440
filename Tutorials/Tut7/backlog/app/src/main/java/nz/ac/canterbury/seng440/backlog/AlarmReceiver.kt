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
        val intent: PendingIntent = Intent(context, PictureActivity::class.java).run {
            PendingIntent.getActivity(context, 0, this, 0)
        }

        val notification = Notification.Builder(context, Notification.CATEGORY_REMINDER).run {
            setSmallIcon(R.drawable.camera)
            setContentTitle("A new day, a new memory")
            setContentText("Just a friendly reminder to take today's picture.")
            setContentIntent(intent)
            setAutoCancel(true)
            build()
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, notification)
    }
}

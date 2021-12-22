package com.privateproject.jetpacklearning.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.privateproject.jetpacklearning.R
import com.privateproject.jetpacklearning.view.MainActivity

class NotificationsHelper(val context: Context) {

    private val CHANNEL_ID = "Dogs channel id"

    private val NOTIFICATION_ID = 123

    private fun createNotifChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val desc = "blah blah"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = desc
            }
            val notManger = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notManger.createNotificationChannel(channel)
        }
    }

    fun createNotification() {

        createNotifChannel()
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_baseline_details_24)

        val notif = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_details_24)
            .setLargeIcon(icon)
            .setContentTitle("text")
            .setContentText("djhfjghrjge")
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null))
            .setContentIntent(pendingIntent) // pending intent bad az click tabdil mishe be intent va app baz mishe
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notif)

    }
}
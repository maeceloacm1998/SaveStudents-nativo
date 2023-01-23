package com.br.core.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.br.core.contract.NotificationManagerContract

class NotificationsManager(private var context: Context) : NotificationManagerContract {
    override fun createChannel(idChannel: String, name: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(idChannel, name, importance)
            val notificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun builderNotification(
        idChannel: String,
        title: String,
        description: String,
        drawable: Int,
        idNotification: Int
    ) {
        val builder = NotificationCompat.Builder(context, idChannel)
            .setSmallIcon(drawable)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            notify(idNotification, builder.build())
        }
    }
}
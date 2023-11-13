package com.savestudents.core.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationManagerImpl(val context: Context) : NotificationManager {
    override fun createChannel(idChannel: String, name: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = android.app.NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(idChannel, name, importance)
            val notificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun builderNotification(
        idChannel: String,
        title: String,
        deepLink: String,
        description: String,
        drawable: Int,
        idNotification: Int
    ) {
        val pendingIntent = handleNotificationClick(deepLink)
        val builder = NotificationCompat.Builder(context, idChannel).setSmallIcon(drawable)
            .setContentTitle(title).setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSmallIcon(com.google.android.gms.auth.api.R.drawable.ic_call_answer_low)
            .setContentIntent(pendingIntent);

        with(NotificationManagerCompat.from(context)) {
            notify(idNotification, builder.build())
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun openAppNotificationSettings() {
        val intent = Intent().apply {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                }

                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    action = "android.settings.APP_NOTIFICATION_SETTINGS"
                    putExtra("app_package", context.packageName)
                    putExtra("app_uid", context.applicationInfo.uid)
                }

                else -> {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    addCategory(Intent.CATEGORY_DEFAULT)
                    data = Uri.parse("package:" + context.packageName)
                }
            }
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun isNotificationEnabled(): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    private fun handleNotificationClick(deeplink: String): PendingIntent? {
        val intent = Intent()

        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(deeplink)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
        )
    }
}
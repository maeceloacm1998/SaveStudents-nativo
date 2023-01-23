package com.br.core.workers.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.br.core.R
import com.br.core.notifications.NotificationsManager
import com.br.core.workers.NotificationWorkerBuilder
import kotlinx.coroutines.delay

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        for (task in 1..900) {
            delay(SIX_HOURS_INTERVAL)
            createNotification("Algoritmos", "teste ${task}", task)

        }
        return Result.success()
    }

    private fun createNotification(title: String, description: String, idNotification: Int) {
        val notificationBuilder = NotificationsManager(applicationContext)
        notificationBuilder.createChannel(
            NotificationWorkerBuilder.ID_CHANNEL,
            NotificationWorkerBuilder.NAME_CHANNEL
        )
        notificationBuilder.builderNotification(
            NotificationWorkerBuilder.ID_CHANNEL,
            title,
            description,
            R.drawable.notification_logo,
            idNotification
        )
    }

    companion object {
        const val SIX_HOURS_INTERVAL: Long = 21600000L
    }
}
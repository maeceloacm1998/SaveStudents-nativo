package com.br.core.workers.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.br.core.R
import com.br.core.notifications.NotificationsManager
import com.br.core.service.external.FirebaseClient
import com.br.core.workers.NotificationWorkerBuilder

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    private val firebaseClient = FirebaseClient()

    override suspend fun doWork(): Result {
        createNotification("Algoritmos", "Testei", 1)

        return Result.success()
    }

    private fun getNotificationTimeline() {

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
}
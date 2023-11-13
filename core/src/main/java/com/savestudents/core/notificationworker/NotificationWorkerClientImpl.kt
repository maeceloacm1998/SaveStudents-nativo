package com.savestudents.core.notificationworker

import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.savestudents.core.notificationworker.worker.NotificationWorkerBuilderImpl
import java.util.concurrent.TimeUnit

class NotificationWorkerClientImpl(private val workManager: WorkManager) :
    NotificationWorkerClient {
    override fun workerEnqueue() {
        handleNotificationWorker()
    }

    private fun handleNotificationWorker() {
        val notificationWorker = PeriodicWorkRequestBuilder<NotificationWorkerBuilderImpl>(
            1, TimeUnit.MILLISECONDS
        ).build()
        workManager.enqueue(notificationWorker)
    }
}
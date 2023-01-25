package com.br.core.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.br.core.workers.worker.NotificationWorker
import java.util.concurrent.TimeUnit

class NotificationWorkerBuilder(private var context: Context) {
    fun workerEnqueue() {
        val workManager = WorkManager.getInstance(context)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val saveRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            1, TimeUnit.DAYS
        ).setConstraints(constraints).build()


        workManager.enqueue(saveRequest)
    }

    companion object {
        const val ID_CHANNEL = "notification_worker"
        const val NAME_CHANNEL = "notification_name"
    }
}
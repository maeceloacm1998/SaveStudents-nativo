package com.savestudents.core.notificationworker.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.savestudents.core.notification.NotificationCustomManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationWorkerBuilderImpl(
    private val appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    private val notificationCustomManager: NotificationCustomManager by inject()
    override suspend fun doWork(): Result {

        handleNotification()
        return Result.success()
    }

    private fun handleNotification() {
        createNotification("Teste", "testeste", "", 1)
    }

    private fun createNotification(
        title: String,
        description: String,
        deeplink: String,
        idNotification: Int
    ) {
        notificationCustomManager.createChannel(ID_CHANNEL, NAME_CHANNEL)
        notificationCustomManager.builderNotification(
            idChannel = ID_CHANNEL,
            title = title,
            deepLink = deeplink,
            description = description,
            drawable = androidx.core.R.drawable.notification_bg,
            idNotification = idNotification
        )
    }

//    private fun handleNotification(notification: NotificationTimeline, index: Int) {
//        notification.timelineList?.map { timelineItem ->
//            val pushToken = SharedPreferencesBuilderR1.GetInstance(applicationContext)
//                .getString(NotificationsManager.PUSH_TOKEN_KEY)
//
//            if (DateUtils.isCurrentDate(timelineItem.date) && notification.pushToken == pushToken) {
//                when (timelineItem.type) {
//                    TIMELINE_TYPE_HOLIDAY -> {
//                        createNotification(
//                            applicationContext.getString(
//                                R.string.title_notification_holiday,
//                                timelineItem.type.uppercase()
//                            ),
//                            applicationContext.getString(R.string.description_notification_holiday),
//                            notification.deeplink,
//                            index
//                        )
//                    }
//                    TIMELINE_TYPE_EXAM -> {
//                        createNotification(
//                            applicationContext.getString(
//                                R.string.title_notification_exam,
//                                notification.shift,
//                                timelineItem.type.uppercase(),
//                                notification.subjectName
//                            ),
//                            applicationContext.getString(
//                                R.string.description_notification_exam,
//                                timelineItem.subjectName
//                            ),
//                            notification.deeplink,
//                            index
//                        )
//                    }
//                    else -> {
//                        createNotification(
//                            applicationContext.getString(
//                                R.string.title_notification_class,
//                                notification.shift,
//                                notification.subjectName
//                            ),
//                            applicationContext.getString(
//                                R.string.description_notification_class,
//                                timelineItem.subjectName
//                            ),
//                            notification.deeplink,
//                            index
//                        )
//                    }
//                }
//            }
//        }
//    }

    companion object {
        const val TIMELINE_TYPE_HOLIDAY = "Feriado"
        const val TIMELINE_TYPE_EXAM = "Prova"
        const val ID_CHANNEL = "notification_channel"
        const val NAME_CHANNEL = "notification_name"
    }
}
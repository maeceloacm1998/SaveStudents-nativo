package com.savestudents.core.notificationservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.firebase.firestore.ktx.toObject
import com.savestudents.core.R
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.notification.NotificationCustomManager
import com.savestudents.core.notificationservice.models.Event
import com.savestudents.core.notificationservice.models.EventType
import com.savestudents.core.notificationservice.models.Schedule
import com.savestudents.core.utils.DateUtils
import com.savestudents.core.utils.DateUtils.getDayOfWeekFromTimestamp
import com.savestudents.core.utils.DateUtils.getTimestampCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class NotificationService : Service() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val notificationCustomManager: NotificationCustomManager by inject()
    private val client: FirebaseClient by inject()
    private val accountManager: AccountManager by inject()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        notificationCustomManager.createChannel(ID_CHANNEL, NAME_CHANNEL)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = buildNotification()
        startForeground(1, notification)

        coroutineScope.launch {
            while (true) {
                startedNotification()
                delay(10000)
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private suspend fun startedNotification() {
        val userId: String? = accountManager.getUserAccount()?.id
        userId?.let {
            client.getSpecificDocument("scheduleUser", it).onSuccess {
                val eventList = it.toObject<Schedule>()?.data
                val eventsOfWeek = eventList?.let { events -> removeEventsNotOfTheWeek(events) }
                handleNotification(eventsOfWeek)
            }
        }
    }

    private fun removeEventsNotOfTheWeek(eventList: List<Event>): List<Event> {
        eventList.forEach { event -> event.events = getEventsItems(event.events) }

        return eventList
    }

    private fun getEventsItems(events: List<Event.EventItem>): MutableList<Event.EventItem> {
        val daysOfWeek = DateUtils.getWeekDatesTimestamp()
        val eventItemList: MutableList<Event.EventItem> = mutableListOf()

        events.forEach { item ->
            val isEvent = item.type == EventType.EVENT.value
            val existEventInWeek = daysOfWeek.contains(item.timestamp)

            eventItemList.add(item)

            if (isEvent && !existEventInWeek) {
                eventItemList.remove(item)
            }
        }

        return eventItemList
    }

    private fun handleNotification(eventList: List<Event>?) {
        eventList?.forEach { event ->
            val currentWeek = getDayOfWeekFromTimestamp(getTimestampCurrentDate())
            if (currentWeek == event.dayName) {
                event.events.forEachIndexed { index, eventItem ->
                    definedNotificationType(event = eventItem, index = index)
                }
            }
        }
    }

    private fun definedNotificationType(event: Event.EventItem, index: Int) {
        when (event.type) {
            EventType.EVENT.value -> {
                createNotification(
                    title = applicationContext.getString(R.string.notification_worker_event_title),
                    description = applicationContext.getString(
                        R.string.notification_worker_event_description,
                        event.matterName
                    ),
                    deeplink = DEEPLINK,
                    idNotification = index
                )
            }

            EventType.MATTER.value -> {
                createNotification(
                    title = getString(
                        R.string.notification_worker_matter_title,
                        event.matterName
                    ),
                    description = getString(
                        R.string.notification_worker_matter_description,
                        event.initialTime
                    ),
                    deeplink = DEEPLINK,
                    idNotification = index
                )
            }
        }
    }

    private fun createNotification(
        title: String,
        description: String,
        deeplink: String,
        idNotification: Int
    ) {

        notificationCustomManager.builderNotification(
            idChannel = ID_CHANNEL,
            title = title,
            deepLink = deeplink,
            description = description,
            drawable = R.drawable.saveicon,
            idNotification = idNotification
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ID_CHANNEL_BACKGROUND,
                "Canal do Serviço em Primeiro Plano",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, ID_CHANNEL_BACKGROUND)
            .setContentTitle("Serviço em Execução")
            .setContentText("Seu serviço está rodando em primeiro plano")
            .setSmallIcon(R.drawable.saveicon)
            .build()
    }

    companion object {
        const val DEEPLINK = ""
        const val ID_CHANNEL = "notification_channel"
        const val ID_CHANNEL_BACKGROUND = "notification_channel_bg"
        const val NAME_CHANNEL = "notification_name"
    }
}
package br.oficial.savestudents.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.br.core.constants.FirestoreDbConstants
import br.oficial.savestudents.databinding.ActivityTimelineSettingsBinding
import br.oficial.savestudents.viewModel.TimelineSettingsViewModel
import br.oficial.savestudents.viewModel.TimelineViewModel
import com.br.core.notifications.NotificationsManager
import com.br.core.service.sharedPreferences.SharedPreferencesBuilder
import com.br.core.workers.NotificationWorkerBuilder
import com.example.data_transfer.model.NotificationTimeline
import com.example.data_transfer.model.TimelineItem

class TimelineSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimelineSettingsBinding
    private lateinit var notificationManager: NotificationsManager
    private val viewModel by lazy { TimelineSettingsViewModel() }
    private val timelineViewModel by lazy { TimelineViewModel() }
    private var timelineItem: TimelineItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimelineSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notificationManager = NotificationsManager(applicationContext)

        observer()

        fetchTimelineItem()
        handleSettingsEnabled()
        handleTimelineNotificationEnabled()
        handleBackButton()
    }

    override fun onResume() {
        super.onResume()
        if (notificationManager.isNotificationEnabled()) {
            binding.notificationEnabledSwitch.isClickable = false
        }
        binding.notificationEnabledSwitch.isChecked = notificationManager.isNotificationEnabled()
    }

    private fun observer() {
        timelineViewModel.informationData.observe(this) {
            timelineItem = it
        }

        viewModel.activeNotificationTimeline.observe(this) {
            binding.notificationTimelineSwitch.isChecked = it
        }
    }

    private fun fetchTimelineItem() {
        val timelineId = intent?.getStringExtra(TIMELINE_ID).toString()
        val pushToken = SharedPreferencesBuilder.GetInstance(applicationContext)
            .getString(NotificationsManager.PUSH_TOKEN_KEY)

        timelineViewModel.getTimelineList(
            FirestoreDbConstants.Collections.TIMELINE_LIST, timelineId
        )
        viewModel.existNotificationTimeline(generateNotificationItemId(timelineId, pushToken))
    }

    private fun handleBackButton() {
        binding.backContainer.setOnClickListener {
            finish()
        }
    }

    private fun handleSettingsEnabled() {
        binding.notificationEnabledSwitch.setOnClickListener {
            notificationManager.openAppNotificationSettings()
        }
        binding.notificationEnabledSwitch.isChecked = notificationManager.isNotificationEnabled()
    }

    private fun handleTimelineNotificationEnabled() {
        binding.notificationTimelineSwitch.setOnClickListener {
            handleChangeTimelineNotification()
        }
    }

    private fun handleChangeTimelineNotification() {
        val timelineNotification = binding.notificationTimelineSwitch
        val pushToken = SharedPreferencesBuilder.GetInstance(applicationContext)
            .getString(NotificationsManager.PUSH_TOKEN_KEY)

        if (timelineNotification.isChecked) {
            addNewNotification(pushToken)
        } else {
            deleteNotification()
        }
    }

    private fun addNewNotification(pushToken: String?) {
        NotificationWorkerBuilder(applicationContext).workerEnqueue()
        timelineItem?.subjectsInformation?.let {
            val notificationTimeline = NotificationTimeline(
                id = generateNotificationItemId(it.id, pushToken),
                deeplink = it.deeplink,
                pushToken = pushToken!!,
                subjectName = it.subjectName,
                shift = it.shift,
                timelineList = timelineItem!!.timelineList
            )
            viewModel.setTimelineNotification(generateNotificationItemId(it.id, pushToken), notificationTimeline)
        }
    }

    private fun deleteNotification() {
        val pushToken = SharedPreferencesBuilder.GetInstance(applicationContext)
            .getString(NotificationsManager.PUSH_TOKEN_KEY)
        timelineItem?.subjectsInformation?.let { viewModel.deleteTimelineNotification("${it.id}${pushToken}") }
    }

    private fun generateNotificationItemId(id: String, pushToken: String?): String {
        return "${id}${pushToken}"
    }

    companion object {
        const val TIMELINE_ID = "timeline_id"

        fun newInstance(context: Context, id: String): Intent {
            val intent = Intent(context, TimelineSettingsActivity::class.java)
            saveBundle(intent, id)
            return intent
        }

        private fun saveBundle(intent: Intent, id: String) {
            val bundle = Bundle().apply {
                this.putString(TIMELINE_ID, id)
            }
            intent.putExtras(bundle)
        }
    }
}
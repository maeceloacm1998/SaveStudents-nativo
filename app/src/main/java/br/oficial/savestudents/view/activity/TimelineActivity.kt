package br.oficial.savestudents.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.oficial.savestudents.constants.FirestoreDbConstants
import br.oficial.savestudents.controller.InformationTimelineController
import br.oficial.savestudents.controller.TimelineController
import br.oficial.savestudents.databinding.ActivityTimelineBinding
import br.oficial.savestudents.model.CreateTimelineItem
import br.oficial.savestudents.utils.DateUtils
import br.oficial.savestudents.viewModel.TimelineViewModel

class TimelineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimelineBinding
    private val timelineController by lazy { TimelineController() }
    private val informationTimelineController by lazy { InformationTimelineController() }
    private val mViewModel by lazy { TimelineViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimelineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchTimelineList()
        handleBackButton()
        handleSettings()
        controller()
        observers()
    }

    private fun controller() {
        binding.timelineRecycleView.apply {
            setController(timelineController)
            layoutManager = LinearLayoutManager(context)
            requestModelBuild()
        }

        binding.informationTimelineContainer.apply {
            setController(informationTimelineController)
            layoutManager = LinearLayoutManager(context)
            requestModelBuild()
        }
    }

    private fun observers() {
        mViewModel.informationData.observe(this) {
            timelineController.setTimelineList(it)
            informationTimelineController.setTimelineList(it)
            informationTimelineController.setLoading(false)
            scrollingToCurrentDay(it.timelineList)
        }
    }

    private fun scrollingToCurrentDay(timelineList: List<CreateTimelineItem>?) {
        val timestampCurrentDay =
            DateUtils.formatDate(DateUtils.getCurrentDay(), DateUtils.DAY_AND_MONTH_DATE)

        timelineList?.mapIndexed { index, createTimelineItem ->
            val date = DateUtils.formatDate(createTimelineItem.date, DateUtils.DAY_AND_MONTH_DATE)
            if (date == timestampCurrentDay) {
                binding.timelineRecycleView.smoothScrollToPosition(index)
            }
        }
    }

    private fun fetchTimelineList() {
        val subjectId = intent?.getStringExtra(SUBJECT_ID).toString()
        informationTimelineController.setLoading(true)
        mViewModel.getTimelineList(FirestoreDbConstants.Collections.TIMELINE_LIST, subjectId)
    }

    private fun handleBackButton() {
        binding.backContainer.setOnClickListener {
            finish()
        }
    }

    private fun handleSettings() {
        binding.settings.setOnClickListener {
            val subjectId = intent?.getStringExtra(SUBJECT_ID).toString()
            startActivity(TimelineSettingsActivity.newInstance(applicationContext, subjectId))
        }
    }

    companion object {
        private const val SUBJECT_ID = "subject_id"

        fun newInstance(context: Context, subjectId: String): Intent {
            val intent = Intent(context, TimelineActivity::class.java)
            saveBundle(intent, subjectId)
            return intent
        }

        private fun saveBundle(intent: Intent, subjectId: String) {
            val bundle = Bundle().apply {
                this.putString(SUBJECT_ID, subjectId)
            }
            intent.putExtras(bundle)
        }
    }
}
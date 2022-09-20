package com.example.savestudents.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savestudents.controller.CreateTimelineController
import com.example.savestudents.databinding.ActivityCreateTimelineBinding
import com.example.savestudents.model.SubjectData
import com.example.savestudents.model.contract.CreateTimelineContract
import com.example.savestudents.model.contract.CreateTimelineItemDialogContract
import com.example.savestudents.view.fragment.CreateTimelineItemDialog
import com.example.savestudents.viewModel.CreateTimelineViewModel

class CreateTimelineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTimelineBinding
    private val controller by lazy { CreateTimelineController(contract) }
    private val viewModel by lazy { CreateTimelineViewModel(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTimelineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller()
        observers()
        setProgressStage()
        handleAddNewTimeline()
        handleBackButton()
        handleTimelineItemList()
    }

    private fun controller() {
        binding.timelineListRv.apply {
            setController(controller)
            layoutManager = LinearLayoutManager(applicationContext)
            requestModelBuild()
        }
    }

    private fun observers() {
        viewModel.timelineItems.observe(this) {
            controller.setTimelineItemsList(it)
        }
    }

    private fun setProgressStage() {
        binding.progressBarCreateTimeline.setStageTitle(STAGE_TITLE)
        binding.progressBarCreateTimeline.setPercentageProgress(PROGRESS_BAR_WIDTH)
    }

    private fun handleAddNewTimeline() {
        binding.addTimelineButton.setOnClickListener {
            CreateTimelineItemDialog(contractDialog).show(supportFragmentManager, CreateTimelineItemDialog.TAG)
        }
    }

    private fun handleBackButton() {
        binding.backContainer.setOnClickListener {
            finish()
        }
    }

    private fun handleTimelineItemList() {
        viewModel.getTimelineItems()
    }

    val contract = object : CreateTimelineContract {
        override fun clickEditButtonListener() {
            CreateTimelineItemDialog(contractDialog).show(supportFragmentManager, CreateTimelineItemDialog.TAG)
        }

        override fun clickDeleteButtonListener(id: Int) {
            viewModel.deleteTimelineItem(id)
            handleTimelineItemList()
        }
    }

    private val contractDialog = object : CreateTimelineItemDialogContract {
        override fun createTimelineItemListener() {
            handleTimelineItemList()
        }
    }

    companion object {
        private const val SUBJECT_NAME = "subject_name"
        private const val TEACHER_NAME = "teacher_name"
        private const val PERIOD_OPTION_NAME = "period_option"
        private const val SHIFT_OPTION_NAME = "shift_name"
        private const val STAGE_TITLE = "Fim"
        private const val PROGRESS_BAR_WIDTH = 0.93F

        fun newInstance(context: Context, subjectData: SubjectData): Intent {
            val intent = Intent(context, CreateTimelineActivity::class.java)
            saveBundle(intent, subjectData)
            return intent
        }

        private fun saveBundle(intent: Intent, subjectData: SubjectData) {
            val bundle = Bundle().apply {
                this.putString(SUBJECT_NAME, subjectData.subjectName)
                this.putString(TEACHER_NAME, subjectData.teacherName)
                this.putString(PERIOD_OPTION_NAME, subjectData.period)
                this.putString(SHIFT_OPTION_NAME, subjectData.shift)
            }
            intent.putExtras(bundle)
        }
    }
}
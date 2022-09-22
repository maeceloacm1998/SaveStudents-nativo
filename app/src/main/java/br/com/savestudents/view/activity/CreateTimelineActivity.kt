package br.com.savestudents.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.savestudents.constants.CreateTimelineConstants
import br.com.savestudents.controller.CreateTimelineController
import br.com.savestudents.databinding.ActivityCreateTimelineBinding
import br.com.savestudents.model.CreateTimelineItem
import br.com.savestudents.model.SubjectData
import br.com.savestudents.model.SubjectList
import br.com.savestudents.model.TimelineItem
import br.com.savestudents.model.contract.CreateTimelineContract
import br.com.savestudents.model.contract.CreateTimelineItemDialogContract
import br.com.savestudents.view.fragment.CreateTimelineItemDialog
import br.com.savestudents.viewModel.CreateTimelineViewModel

class CreateTimelineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTimelineBinding
    private val controller by lazy { CreateTimelineController(contract) }
    private val viewModel by lazy { CreateTimelineViewModel(applicationContext) }
    private var timelineItemsList: MutableList<CreateTimelineItem> = mutableListOf()

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
        handleSubmitButton()
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
            timelineItemsList = it
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

    private fun handleSubmitButton() {
        binding.buttonSubmit.setOnClickListener {
            if(validateTimelineItems()) {
                val documentId = viewModel.createDocument(CreateTimelineConstants.Collection.SUBJECT_LIST_COLLECTION)
                createSubject(documentId)
                createTimeline(documentId)
                viewModel.clearTimelineItemList()
                startActivity(CreateSubjectActivity.newInstance(applicationContext))
            } else {
                Toast.makeText(applicationContext, "Crie pelomenos uma matéria", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateTimelineItems(): Boolean {
        if(timelineItemsList.isNotEmpty()) {
            return true
        }
        return false
    }

    private fun createSubject(id: String) {
        val subjectItem = createSubjectItem(id)
        viewModel.createSubject(CreateTimelineConstants.Collection.SUBJECT_LIST_COLLECTION, id, subjectItem)
    }

    private fun createTimeline(id: String) {
        val timelineItem = createTimelineItem(id)
        viewModel.createTimeline(CreateTimelineConstants.Collection.TIMELINE_LIST_COLLECTION, id, timelineItem)
    }

    private fun createSubjectItem(id: String): SubjectList {
        return SubjectList().apply {
            this.id = id
            this.period = intent?.getStringExtra(PERIOD_OPTION_NAME).toString()
            this.shift = intent?.getStringExtra(SHIFT_OPTION_NAME).toString()
            this.subjectName = intent?.getStringExtra(SUBJECT_NAME).toString()
            this.teacherName = intent?.getStringExtra(TEACHER_NAME).toString()
        }
    }

    private fun createTimelineItem(id: String): TimelineItem {
        return TimelineItem().apply {
            this.subjectsInformation = createSubjectItem(id)
            this.timelineList = timelineItemsList
        }
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
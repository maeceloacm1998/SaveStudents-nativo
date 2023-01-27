package br.oficial.savestudents.debug_mode.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.oficial.savestudents.constants.CreateTimelineConstants
import br.oficial.savestudents.databinding.ActivityCreateTimelineBinding
import br.oficial.savestudents.debug_mode.controller.CreateTimelineController
import br.oficial.savestudents.debug_mode.model.contract.CreateTimelineContract
import br.oficial.savestudents.debug_mode.model.contract.CreateTimelineItemDialogContract
import br.oficial.savestudents.debug_mode.model.contract.EditTimelineItemDialogContract
import br.oficial.savestudents.debug_mode.view.fragment.CreateTimelineItemDialog
import br.oficial.savestudents.debug_mode.view.fragment.EditTimelineItemDialog
import br.oficial.savestudents.debug_mode.viewModel.CreateTimelineViewModel
import com.br.core.service.internal.dao.CreateTimelineDAO
import com.br.core.service.internal.database.CreateTimelineItemsDB
import com.example.data_transfer.model.*
import com.example.data_transfer.model.entity.CreateTimelineItemEntity

class CreateTimelineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTimelineBinding
    private lateinit var timelineItemDAO: CreateTimelineDAO
    private val controller by lazy { CreateTimelineController(contract) }
    private val viewModel by lazy { CreateTimelineViewModel(applicationContext) }
    private var timelineItemsList: MutableList<CreateTimelineItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTimelineBinding.inflate(layoutInflater)
        timelineItemDAO = CreateTimelineItemsDB.getDataBase(applicationContext).createTimelineDAO()
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
            CreateTimelineItemDialog(contractDialog).show(
                supportFragmentManager,
                CreateTimelineItemDialog.TAG
            )
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
            if (validateTimelineItems()) {
                createSubjectItem()
                finishCreateSubjectItem()
            } else {
                Toast.makeText(applicationContext, "Crie pelomenos uma matéria", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun createSubjectItem() {
        val documentId =
            viewModel.createDocument(CreateTimelineConstants.Collection.SUBJECT_LIST_COLLECTION)
        createSubject(documentId)
        createTimeline(documentId)
        viewModel.clearTimelineItemList()
    }

    private fun finishCreateSubjectItem() {
        val intent = Intent(this, AllSubjectsListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun validateTimelineItems(): Boolean {
        if (timelineItemsList.isNotEmpty()) {
            return true
        }
        return false
    }

    private fun createSubject(id: String) {
        val subjectItem = createSubjectItem(id)
        viewModel.createSubject(
            CreateTimelineConstants.Collection.SUBJECT_LIST_COLLECTION,
            id,
            subjectItem
        )
    }

    private fun createTimeline(id: String) {
        val timelineItem = createTimelineItem(id)
        viewModel.createTimeline(
            CreateTimelineConstants.Collection.TIMELINE_LIST_COLLECTION,
            id,
            timelineItem
        )
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

    private fun updateTimelineItem(timeline: CreateTimelineItem) {
        val item = timelineItemsList.find { it.id == timeline.id }
        val index = timelineItemsList.indexOf(item)
        timelineItemsList[index] = timeline

        controller.setTimelineItemsList(timelineItemsList)
    }

    val contract = object : CreateTimelineContract {
        override fun clickEditButtonListener(timelineItem: CreateTimelineItem) {
            EditTimelineItemDialog(editContractDialog, timelineItem).show(
                supportFragmentManager,
                CreateTimelineItemDialog.TAG
            )
        }

        override fun clickDeleteButtonListener(id: Int) {
            viewModel.deleteTimelineItem(id)
            handleTimelineItemList()
        }
    }

    private val contractDialog = object : CreateTimelineItemDialogContract {
        override fun createTimelineItemListener(timelineItem: CreateTimelineItemEntity) {
            timelineItemDAO.createTimelineItems(timelineItem)
            handleTimelineItemList()
        }
    }

    private val editContractDialog = object : EditTimelineItemDialogContract {
        override fun editTimelineItemListener(timelineItem: com.example.data_transfer.model.entity.CreateTimelineItemEntity) {
            timelineItemDAO.updateTimelineItemPerId(
                timelineItem.id,
                timelineItem.date,
                timelineItem.subjectName,
                timelineItem.type
            )

            updateTimelineItem(timelineItem.asDomainModel())
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
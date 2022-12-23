package br.com.savestudents.debug_mode.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.savestudents.databinding.ActivityEditTimelineBinding
import br.com.savestudents.debug_mode.controller.CreateTimelineController
import br.com.savestudents.debug_mode.model.contract.CreateTimelineContract
import br.com.savestudents.debug_mode.model.contract.CreateTimelineItemDialogContract
import br.com.savestudents.debug_mode.model.contract.EditTimelineItemDialogContract
import br.com.savestudents.debug_mode.view.fragment.CreateTimelineItemDialog
import br.com.savestudents.debug_mode.view.fragment.EditTimelineItemDialog
import br.com.savestudents.debug_mode.viewModel.EditTimelineViewModel
import br.com.savestudents.model.CreateTimelineItem
import br.com.savestudents.model.TimelineItem
import br.com.savestudents.model.asDomainModel
import br.com.savestudents.service.internal.entity.CreateTimelineItemEntity

class EditTimelineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTimelineBinding
    private lateinit var timelineItem: TimelineItem
    private val controller by lazy { CreateTimelineController(contract) }
    private val viewModel by lazy { EditTimelineViewModel() }
    private var timelineItemsList: MutableList<CreateTimelineItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTimelineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller()
        fetchTimelineItem()
        observers()
        handleAddNewTimeline()
        handleBackButton()
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
        viewModel.timelineItem.observe(this) { timelineItem ->
            controller.setTimelineItemsList(timelineItem.timelineList!!)
            timelineItemsList = timelineItem.timelineList!!
            this.timelineItem = timelineItem
        }

        viewModel.updateTimelineItem.observe(this) {
            Toast.makeText(
                applicationContext,
                "Cronograma atualizado com sucesso.",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun fetchTimelineItem() {
        val id = intent?.getStringExtra(ID)
        id?.let { viewModel.getSubjectItemPerId(it) }
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

    private fun handleSubmitButton() {
        binding.buttonSubmit.setOnClickListener {
            if (validateTimelineItems()) {
                updateTimelineListItem(timelineItem)
            } else {
                Toast.makeText(applicationContext, "Crie pelomenos uma matéria", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun updateTimelineListItem(timelineItem: TimelineItem) {
        viewModel.updateTimelineItem(timelineItem)
    }

    private fun validateTimelineItems(): Boolean {
        if (timelineItemsList.isNotEmpty()) {
            return true
        }
        return false
    }

    private fun createTimelineItem(timelineItem: CreateTimelineItem) {
        timelineItemsList.add(timelineItem)
        controller.setTimelineItemsList(timelineItemsList)
    }

    private fun deleteTimelineItem(id: String) {
        val filterElementDelete = timelineItemsList.find { item -> item.id.toString() == id }
        timelineItemsList.remove(filterElementDelete)
        controller.setTimelineItemsList(timelineItemsList)
    }

    private fun updateTimelineItem(timeline: CreateTimelineItem) {
        val item = timelineItemsList.find { it.id == timeline.id }
        val index = timelineItemsList.indexOf(item)
        timelineItemsList[index] = timeline

        controller.setTimelineItemsList(timelineItemsList)
        timelineItem.copy(timelineList = timelineItemsList)
    }


    val contract = object : CreateTimelineContract {
        override fun clickEditButtonListener(timelineItem: CreateTimelineItem) {
            EditTimelineItemDialog(editContractDialog, timelineItem).show(
                supportFragmentManager,
                EditTimelineItemDialog.TAG
            )
        }

        override fun clickDeleteButtonListener(id: Int) {
            deleteTimelineItem(id.toString())
        }
    }

    private val contractDialog = object : CreateTimelineItemDialogContract {
        override fun createTimelineItemListener(timelineItem: CreateTimelineItemEntity) {
            createTimelineItem(timelineItem.asDomainModel())
        }
    }

    private val editContractDialog = object : EditTimelineItemDialogContract {
        override fun editTimelineItemListener(timelineItem: CreateTimelineItemEntity) {
            updateTimelineItem(timelineItem.asDomainModel())
        }
    }

    companion object {
        private const val ID = "id"

        fun newInstance(context: Context, id: String?): Intent {
            val intent = Intent(context, EditTimelineActivity::class.java)
            saveBundle(intent, id)
            return intent
        }

        private fun saveBundle(intent: Intent, id: String?) {
            val bundle = Bundle().apply {
                this.putString(ID, id)
            }
            intent.putExtras(bundle)
        }
    }
}
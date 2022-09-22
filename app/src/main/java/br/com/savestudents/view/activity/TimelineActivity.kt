package br.com.savestudents.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.savestudents.constants.FirestoreDbConstants
import br.com.savestudents.controller.TimelineController
import br.com.savestudents.databinding.ActivityTimelineBinding
import br.com.savestudents.model.contract.TimelineContract
import br.com.savestudents.viewModel.TimelineViewModel

class TimelineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimelineBinding
    private val timelineController by lazy { TimelineController(timelineContract) }
    private lateinit var mViewModel: TimelineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimelineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(TimelineViewModel()::class.java)

        fetchTimelineList()
        handleBackButton()
        controller()
        observers()
    }

    private fun controller() {
        binding.timelineRecycleView.apply {
            setController(timelineController)
            layoutManager = LinearLayoutManager(context)
            requestModelBuild()
        }
    }

    private fun observers() {
        mViewModel.informationData.observe(this) {
            timelineController.setTimelineList(it)
            timelineController.setLoading(false)
        }
    }

    private fun fetchTimelineList() {
        timelineController.setLoading(true)
        mViewModel.getTimelineList(FirestoreDbConstants.Collections.TIMELINE_LIST, intent?.getStringExtra(SUBJECT_ID).toString())
    }

    private fun handleBackButton() {
        binding.backContainer.setOnClickListener {
            finish()
        }
    }

    private val timelineContract = object : TimelineContract {

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
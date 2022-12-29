package br.oficial.savestudents.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.oficial.savestudents.constants.FirestoreDbConstants
import br.oficial.savestudents.controller.TimelineController
import br.oficial.savestudents.databinding.ActivityTimelineBinding
import br.oficial.savestudents.model.contract.TimelineContract
import br.oficial.savestudents.viewModel.TimelineViewModel

class TimelineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimelineBinding
    private val timelineController by lazy { TimelineController(timelineContract) }
    private val mViewModel by lazy {TimelineViewModel()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimelineBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val subjectId = intent?.getStringExtra(SUBJECT_ID).toString()
        timelineController.setLoading(true)
        mViewModel.getTimelineList(FirestoreDbConstants.Collections.TIMELINE_LIST, subjectId)
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
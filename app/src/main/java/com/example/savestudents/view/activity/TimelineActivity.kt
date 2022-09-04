package com.example.savestudents.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savestudents.controller.TimelineController
import com.example.savestudents.databinding.ActivityTimelineBinding
import com.example.savestudents.model.contract.TimelineContract
import com.example.savestudents.viewModel.TimelineViewModel

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

        controller()
        mViewModel.getInformation("teste")
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
            val r = ""
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
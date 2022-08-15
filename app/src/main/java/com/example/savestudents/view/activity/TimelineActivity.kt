package com.example.savestudents.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.savestudents.R

class TimelineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
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
package br.com.savestudents.debug_mode.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.savestudents.R
import br.com.savestudents.constants.CreateSubjectConstants
import br.com.savestudents.databinding.ActivityCreateSubjectBinding
import br.com.savestudents.model.SubjectData

class CreateSubjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateSubjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleGoBackButton()
        handlePeriodSelect()
        handleShiftSelect()
        handleNextButton()
        disableNextButton()
        handleProgressBar()
    }

    override fun onResume() {
        super.onResume()
        if (!periodSelected.isNullOrBlank()) binding.selectPeriodList.handleTitle(periodSelected)
        if (!shiftSelected.isNullOrBlank()) {
            binding.selectShiftList.handleTitle(shiftSelected)
            enabledNextButton()
        }
    }

    private fun handleGoBackButton() {
        binding.backContainer.setOnClickListener {
            finish()
        }
    }

    private fun handlePeriodSelect() {
        binding.selectPeriodList.view().setOnClickListener {
            handlePeriodSelect(CreateSubjectConstants.Filter.PERIOD_FIELD)
        }
    }

    private fun handleShiftSelect() {
        binding.selectShiftList.view().setOnClickListener {
            handlePeriodSelect(CreateSubjectConstants.Filter.SHIFT_FIELD)
        }
    }

    private fun handlePeriodSelect(filterType: String) {
        val intent = SelectOptionActivity.newInstance(applicationContext, filterType)
        startActivity(intent)
    }

    private fun handleNextButton() {
        binding.buttonSubmit.setOnClickListener {
            val subjectName = binding.subjectName.editText().text.toString().trim()
            val teacherName = binding.teacherName.editText().text.toString().trim()

            if (validationData(subjectName, teacherName, periodSelected, shiftSelected)) {
                saveData(subjectName, teacherName, periodSelected!!, shiftSelected!!)
            }
        }
    }

    private fun validationData(
        subjectName: String?,
        teacherName: String?,
        periodSelected: String?,
        shiftSelected: String?
    ): Boolean {
        if (!subjectName.isNullOrBlank() && !teacherName.isNullOrBlank() && !periodSelected.isNullOrBlank() && !shiftSelected.isNullOrBlank()) {
            return true
        } else {
            Toast.makeText(applicationContext, "Preencha todos os campos", Toast.LENGTH_SHORT)
                .show()
        }

        return false
    }

    private fun saveData(
        subjectName: String,
        teacherName: String,
        periodSelected: String,
        shiftSelected: String
    ) {
        val subjectData = SubjectData(subjectName, teacherName, periodSelected, shiftSelected)
        startActivity(CreateTimelineActivity.newInstance(applicationContext, subjectData))
    }

    private fun disableNextButton() {
        binding.buttonSubmit.isEnabled = false
        binding.buttonSubmit.setBackgroundDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.bg_disabled_rounded_primary
            )
        )
    }

    private fun enabledNextButton() {
        binding.buttonSubmit.isEnabled = true
        binding.buttonSubmit.setBackgroundDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.bg_rounded_primary
            )
        )
    }

    private fun handleProgressBar() {
        binding.progressBarCreateSubject.setPercentageProgress(PROGRESS_BAR_WIDTH)
        binding.progressBarCreateSubject.setStageTitle(STAGE_TITLE)
    }

    companion object {
        private var periodSelected: String? = null
        private var shiftSelected: String? = null
        private const val PROGRESS_BAR_WIDTH: Float = 0.5F
        private var STAGE_TITLE: String = "Etapa 1"

        fun newInstance(context: Context): Intent {
            return Intent(context, CreateSubjectActivity::class.java)
        }

        fun setOptionSelected(type: String, option: String) {
            when (type) {
                CreateSubjectConstants.Filter.PERIOD_FIELD -> {
                    periodSelected = option
                }

                CreateSubjectConstants.Filter.SHIFT_FIELD -> {
                    shiftSelected = option
                }
            }
        }
    }
}
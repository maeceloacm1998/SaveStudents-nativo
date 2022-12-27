package br.oficial.savestudents.debug_mode.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import br.oficial.savestudents.constants.CreateSubjectConstants
import br.oficial.savestudents.databinding.ActivityEditSubjectBinding
import br.oficial.savestudents.debug_mode.constants.EditSubjectConstants
import br.oficial.savestudents.debug_mode.viewModel.EditSubjectViewModel
import br.oficial.savestudents.model.SubjectList

class EditSubjectActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditSubjectBinding
    private val viewModel by lazy { EditSubjectViewModel() }
    private lateinit var subjectItem: SubjectList

    private var modifiedPeriodOrShift: Boolean = false
    private var modifiedSubjectName: Boolean = false
    private var modifiedTeacherName: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchSubjectItem()
        observers()

        handleGoBackListener()
        handleClickPeriodSelectListener()
        handleClickShiftSelectListener()
        handleSubmitListener()
        checkModifiedSubjectAndTeacher()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SelectOptionActivity.SELECT_OPTION_REQUEST_CODE) {
            val filterOption: String =
                data?.getStringExtra(SelectOptionActivity.FILTER_OPTION).toString()
            val filter: String =
                data?.getStringExtra(SelectOptionActivity.FILTER_VALUE_SELECTED).toString()
            modifiedPeriodOrShift = true

            setOptionSelected(filterOption, filter)
        }
    }

    private fun setOptionSelected(type: String, option: String) {
        when (type) {
            CreateSubjectConstants.Filter.PERIOD_FIELD -> {
                binding.selectPeriodList.handleTitle(option)
                val newSubjectItem = subjectItem.copy(period = option)
                subjectItem = newSubjectItem
            }

            CreateSubjectConstants.Filter.SHIFT_FIELD -> {
                binding.selectShiftList.handleTitle(option)
                val newSubjectItem = subjectItem.copy(shift = option)
                subjectItem = newSubjectItem
            }
        }
    }

    private fun fetchSubjectItem() {
        intent?.getStringExtra(ID_KEY)?.let {
            viewModel.getSubjectPerId(it)
        }
    }

    private fun handleGoBackListener() {
        binding.backContainer.setOnClickListener {
            finish()
        }
    }

    private fun observers() {
        viewModel.subjectItemResponse.observe(this) { subjectItem ->
            this.subjectItem = subjectItem
            handleSubjectName(subjectItem.subjectName)
            handleTeacherName(subjectItem.teacherName)
            handlePeriodSelectList(subjectItem.period)
            handleShiftSelectList(subjectItem.shift)
            loading(false)
        }

        viewModel.updateSubjectItemSuccess.observe(this) { observer ->
            loading(false)
            setResult(EDIT_SUBJECT_REQUEST_CODE)
            finish()
            Toast.makeText(
                this,
                EditSubjectConstants.Toast.UPDATE_SUBJECT_SUCCESS,
                Toast.LENGTH_SHORT
            ).show()
        }

        viewModel.updateSubjectItemError.observe(this) { observer ->
            loading(false)
            Toast.makeText(this, observer.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleClickPeriodSelectListener() {
        binding.selectPeriodList.view().setOnClickListener {
            handlePeriodSelect(CreateSubjectConstants.Filter.PERIOD_FIELD)
        }
    }

    private fun handleClickShiftSelectListener() {
        binding.selectShiftList.view().setOnClickListener {
            handlePeriodSelect(CreateSubjectConstants.Filter.SHIFT_FIELD)
        }
    }

    private fun handlePeriodSelect(filterType: String) {
        val intent = SelectOptionActivity.newInstance(applicationContext, filterType)
        startActivityForResult(intent, SelectOptionActivity.SELECT_OPTION_REQUEST_CODE)
    }

    private fun handleSubjectName(subjectName: String) {
        binding.subjectName.editText().setText(subjectName)
    }

    private fun handleTeacherName(teacherName: String) {
        binding.teacherName.editText().setText(teacherName)
    }

    private fun handlePeriodSelectList(period: String) {
        binding.selectPeriodList.handleTitle(period)
    }

    private fun handleShiftSelectList(shift: String) {
        binding.selectShiftList.handleTitle(shift)
    }

    private fun checkModifiedSubjectAndTeacher() {
        binding.subjectName.editText().addTextChangedListener {
            if (!it?.toString().equals(subjectItem.subjectName)) {
                modifiedSubjectName = true
                subjectItem = subjectItem.copy(subjectName = it.toString())
            } else {
                modifiedSubjectName = false
                subjectItem = subjectItem.copy(subjectName = it.toString())
            }
        }

        binding.teacherName.editText().addTextChangedListener {
            if (!it?.toString().equals(subjectItem.teacherName)) {
                modifiedTeacherName = true
                subjectItem = subjectItem.copy(teacherName = it.toString())
            } else {
                modifiedTeacherName = false
                subjectItem = subjectItem.copy(teacherName = it.toString())
            }
        }
    }

    private fun handleSubmitListener() {
        binding.buttonSubmit.setOnClickListener {
            if (isModifiedItem()) {
                val id = intent?.getStringExtra(ID_KEY)
                id?.let {
                    loading(true)
                    viewModel.updateSubjectItem(it, subjectItem)
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    EditSubjectConstants.Toast.FIELD_NOT_MODIFIED,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loading(status: Boolean) {
        if (status) {
            binding.loading.frameLayout.visibility = View.VISIBLE
        } else {
            binding.loading.frameLayout.visibility = View.GONE
        }
    }

    private fun isModifiedItem(): Boolean {
        return modifiedPeriodOrShift || modifiedSubjectName || modifiedTeacherName
    }

    companion object {
        const val ID_KEY = "id_key"
        const val EDIT_SUBJECT_REQUEST_CODE = 0

        fun newInstance(id: String?, context: Context): Intent {
            val intent = Intent(context, EditSubjectActivity::class.java)
            saveBundle(intent, id)
            return intent
        }

        private fun saveBundle(intent: Intent, id: String?) {
            val bundle = Bundle().apply {
                this.putString(ID_KEY, id)
            }
            intent.putExtras(bundle)
        }
    }
}
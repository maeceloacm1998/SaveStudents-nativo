package br.com.savestudents.debug_mode.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.savestudents.databinding.ActivityEditSubjectBinding
import br.com.savestudents.model.SubjectList

class EditSubjectActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditSubjectBinding
    private var subjectSelectedData: SubjectList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleSubjectName()
        handleTeacherName()
    }

    private fun handleSubjectName() {
        if(subjectSelectedData != null) {
            binding.subjectName.editText().setText(subjectSelectedData?.subjectName)
        }
    }

    private fun handleTeacherName() {
        if(subjectSelectedData != null) {
            binding.teacherName.editText().setText(subjectSelectedData?.teacherName)
        }
    }

    companion object {
        const val ID_KEY = "id_key"

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
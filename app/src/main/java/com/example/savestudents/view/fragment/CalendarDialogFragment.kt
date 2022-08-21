package com.example.savestudents.view.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.savestudents.R
import com.example.savestudents.databinding.FragmentCatalogDialogBinding
import com.example.savestudents.view.activity.TimelineActivity

class CalendarDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentCatalogDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.CalendarModalDialog)
    }


    companion object {
        const val TAG = "CalendarDialogModal"
        private const val SUBJECT_ID = "subject_id"

        fun newInstance(context: Context): CalendarDialogFragment {
            val intent = Intent(context, CalendarDialogFragment::class.java)
            saveBundle(intent)
            return CalendarDialogFragment()
        }

        private fun saveBundle(intent: Intent) {
            val bundle = Bundle().apply {
                this.putString(SUBJECT_ID, "subjectId")
            }

            intent.putExtras(bundle)
        }
    }
}
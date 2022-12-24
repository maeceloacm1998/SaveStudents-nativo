package br.com.savestudents.ui_component.confirmationAlertDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.savestudents.R
import br.com.savestudents.databinding.ConfirmationAlertDialogBinding

class ConfirmationAlertDialog(private val mContract: ConfirmationAlertContract) : DialogFragment() {
    lateinit var binding: ConfirmationAlertDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ConfirmationAlertDialogBinding.inflate(inflater, container, false)
        setTitle()
        setDescription()
        clickCancelButton()
        clickConfirmButton()
        return binding.root
    }

    private fun setTitle() {
        title.let { binding.title.text = it ?: "Title" }
    }

    private fun setDescription() {
        description.let { binding.description.text = it ?: "Description" }
    }

    private fun clickCancelButton() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }


    private fun clickConfirmButton() {
        binding.confirmButton.setOnClickListener {
            mId?.let { id -> mContract.clickConfirmButtonListener(id) }
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Theme_Dialog)
    }

    companion object {
        const val TAG = "confirmation_alert_dialog"
        private var mId: String? = null
        private var title: String? = null
        private var description: String? = null

        fun saveBundle(id: String, title: String, description: String) {
            this.mId = id
            this.title = title
            this.description = description
        }
    }
}
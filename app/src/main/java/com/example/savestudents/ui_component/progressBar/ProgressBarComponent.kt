package com.example.savestudents.ui_component.simpleEditText

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.savestudents.databinding.ProgressBarComponentBinding
import com.google.android.material.textfield.TextInputLayout


class ProgressBarComponent @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet?,
    defStyledAttr: Int = 0
) : TextInputLayout(context, attr, defStyledAttr) {
    private val binding: ProgressBarComponentBinding = ProgressBarComponentBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        setPercentageProgress(DEFAULT_PERCENTAGE)
        setStageTitle(DEFAULT_STAGE_TITLE)
    }

    fun setStageTitle(title: String) {
        binding.titleStage.text = title
    }

    fun setPercentageProgress(percentage: Float) {
        (binding.progressBar.layoutParams as ConstraintLayout.LayoutParams)
            .matchConstraintPercentWidth = percentage
        binding.progressBar.requestLayout()
    }

    companion object {
        private const val DEFAULT_PERCENTAGE = 0.1F
        private const val DEFAULT_STAGE_TITLE = "Etapa 1"
    }
}
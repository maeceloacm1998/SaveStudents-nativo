package com.example.savestudents.ui_component.simpleEditText

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import com.example.savestudents.R
import com.example.savestudents.databinding.SimpleEditTextComponentBinding
import com.google.android.material.textfield.TextInputLayout


class SimpleEditTextCustomView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet?,
    defStyledAttr: Int = 0
) : TextInputLayout(context, attr, defStyledAttr) {

    private var hint: String? = null

    private val binding: SimpleEditTextComponentBinding = SimpleEditTextComponentBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        setLayout(attr)
        setDefaultBackgroundEditText(context)
        handleHint(hint)
    }

    private fun setLayout(attr: AttributeSet?) {
        attr?.let { attributeSet: AttributeSet ->
            val attributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.SimpleEditTextCustomView)
            attributesHint(attributes)
            attributes.recycle()
        }
    }

    private fun attributesHint(attributes: TypedArray) {
        val hintResId =
            attributes.getResourceId(R.styleable.SimpleEditTextCustomView_hint, 0)
        if (hintResId != 0) {
            hint = context.getString(hintResId)
        }
    }

    fun handleHint(hintValue: String?) {
        binding.searchBar.hint = hintValue
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setDefaultBackgroundEditText(context: Context) {
        binding.searchBarContainer.setBackgroundDrawable(context.getDrawable(R.drawable.bg_rounded_blue_light))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setBackgroundClickEditText(context: Context) {
        binding.searchBarContainer.setBackgroundDrawable(context.getDrawable(R.drawable.bg_rounded_blue_light_with_border_primary))
    }

    fun editText(): EditText {
        return binding.searchBar
    }
}
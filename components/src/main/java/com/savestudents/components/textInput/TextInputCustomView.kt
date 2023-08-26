package com.savestudents.components.textInput

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.AutoCompleteTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.savestudents.components.R
import com.savestudents.components.databinding.TextInputCustomViewBinding

class TextInputCustomView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {
    private var binding: TextInputCustomViewBinding = TextInputCustomViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    val editTextDefault: TextInputEditText = binding.editTextDefault
    val editTextAutocomplete: AutoCompleteTextView = binding.editTextAutocomplete

    var layoutType: Int = TextInputLayoutType.DEFAULT.value
        set(value) {
            field = value
            handleLayoutType(layoutType)
        }

    var hint: String = ""
        set(value) {
            field = value
            binding.run {
                textInputAutocomplete.hint = value
                textInputDefault.hint = value
            }
        }

    var error: String = ""
        set(value) {
            field = value
            binding.textInputDefault.error = value
        }

    var helper: String = ""
        set(value) {
            field = value
            binding.textInputDefault.helperText = value
            binding.textInputAutocomplete.helperText = value
        }

    var inputType: Int = InputType.TYPE_CLASS_TEXT
        set(value) {
            field = value
            binding.editTextDefault.inputType = value
        }

    var passwordToggleEnabled: Boolean = false
        set(value) {
            field = value
            binding.run {
                textInputDefault.isPasswordVisibilityToggleEnabled = value
            }
        }

    var setAutocompleteItems: List<String> = mutableListOf()
        set(value) {
            field = value
            binding.editTextAutocomplete.setSimpleItems(value.toTypedArray())
            binding.editTextAutocomplete
        }


    private lateinit var onAutoCompleteItemSelected: (item: String) -> Unit?

    init {
        setLayout(attrs)
        setupViews()
    }

    private fun setLayout(attr: AttributeSet?) {
        attr?.let { attributeSet: AttributeSet ->
            val attributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.TextInputCustomView)
            layoutType = attributes.getInt(
                R.styleable.TextInputCustomView_layoutType, TextInputLayoutType.DEFAULT.value
            )
            hint = attributes.getString(R.styleable.TextInputCustomView_android_hint) ?: ""
            helper = attributes.getString(R.styleable.TextInputCustomView_helperText) ?: ""
            inputType = attributes.getInt(
                R.styleable.TextInputCustomView_android_inputType,
                InputType.TYPE_CLASS_TEXT
            )
            passwordToggleEnabled =
                attributes.getBoolean(R.styleable.TextInputCustomView_passwordToggleEnabled, false)
            attributes.recycle()
        }
    }

    private fun setupViews() {
        binding.editTextAutocomplete.run {
            setOnItemClickListener { _, _, _, _ ->
                if (::onAutoCompleteItemSelected.isInitialized) {
                    onAutoCompleteItemSelected(text.toString())
                }
            }
        }
    }

    private fun handleLayoutType(layoutType: Int) {
        when (layoutType) {
            TextInputLayoutType.AUTOCOMPLETE.value -> handleTextInputAutocomplete()
            else -> handleTextInputDefault()
        }
    }


    private fun handleTextInputAutocomplete() {
        binding.run {
            textInputDefault.isVisible = false
            textInputAutocomplete.isVisible = true
        }
    }

    private fun handleTextInputDefault() {
        binding.run {
            textInputDefault.isVisible = true
            textInputAutocomplete.isVisible = false
        }
    }

    fun onItemSelected(listener: (item: String) -> Unit) {
        onAutoCompleteItemSelected = listener
    }
}
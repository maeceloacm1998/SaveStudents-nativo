package br.com.savestudents.ui_component.simpleEditText

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import androidx.core.content.ContextCompat
import br.com.savestudents.R
import br.com.savestudents.databinding.SimpleEditTextComponentBinding
import com.google.android.material.textfield.TextInputLayout


enum class PasswordState {
    SHOW,
    CLOSE
}

class SimpleEditTextCustomView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet?,
    defStyledAttr: Int = 0
) : TextInputLayout(context, attr, defStyledAttr) {

    private var hint: String? = null
    private var isPasswordEditText: Boolean? = null
    private var statePasswordButton: PasswordState = PasswordState.CLOSE

    private val binding: SimpleEditTextComponentBinding = SimpleEditTextComponentBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        setLayout(attr)
        setDefaultBackgroundEditText(context)
        handleHint(hint)
        showPasswordButton(isPasswordEditText)
        handleClickPasswordButton()
        if(isPasswordEditText == true) {
            setClosePassword(context)
        }
    }

    private fun setLayout(attr: AttributeSet?) {
        attr?.let { attributeSet: AttributeSet ->
            val attributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.SimpleEditTextCustomView)
            attributesHint(attributes)
            attributesIsPasswordEditText(attributes)
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

    private fun attributesIsPasswordEditText(attributes: TypedArray) {
        isPasswordEditText =
            attributes.getBoolean(R.styleable.SimpleEditTextCustomView_isPasswordEditText, false)
    }

    fun handleHint(hintValue: String?) {
        binding.searchBar.hint = hintValue
    }

    fun showPasswordButton(showPasswordButton: Boolean?) {
        showPasswordButton.let { state ->
            if (state == true) {
                binding.showPasswordButton.visibility = VISIBLE
            } else {
                binding.showPasswordButton.visibility = GONE
            }
        }
    }

    fun setError(message: String) {
        binding.textError.text = message
        binding.searchBar.setBackgroundDrawable(ContextCompat.getDrawable(context ,R.drawable.bg_rounded_error_with_border))
        binding.showPasswordButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_rounded_error))
    }

    private fun handleClickPasswordButton() {
        binding.showPasswordButton.setOnClickListener {
            if (statePasswordButton == PasswordState.CLOSE) {
                setShowPassword(context)
            } else {
                setClosePassword(context)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setShowPassword(context: Context) {
        binding.showPasswordButton.setImageDrawable(context.getDrawable(R.drawable.ic_show_eye))
        binding.showPasswordButton.setColorFilter(ContextCompat.getColor(context, R.color.secondary_700))
        binding.searchBar.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT

        statePasswordButton = PasswordState.SHOW
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setClosePassword(context: Context) {
        binding.showPasswordButton.setImageDrawable(context.getDrawable(R.drawable.ic_close_eye))
        binding.showPasswordButton.setColorFilter(ContextCompat.getColor(context, R.color.secondary_700))
        binding.searchBar.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        statePasswordButton = PasswordState.CLOSE
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
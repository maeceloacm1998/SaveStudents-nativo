package br.oficial.savestudents.ui_component.inputChips

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import br.oficial.savestudents.R
import br.oficial.savestudents.databinding.InputChipsBinding
import com.google.android.material.textfield.TextInputLayout

class InputChipsCustomView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet?,
    defStyledAttr: Int = 0
) : TextInputLayout(context, attr, defStyledAttr) {
    private var title: String? = null
    private var mSelected: Boolean = false

    private val binding: InputChipsBinding = InputChipsBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        setLayout(attr)
        handleTitle(title)
    }

    private fun setLayout(attr: AttributeSet?) {
        attr?.let { attributeSet: AttributeSet ->
            val attributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.InputChipsCustomView)
            attributesInputTitle(attributes)
            attributes.recycle()
        }
    }

    private fun attributesInputTitle(attributes: TypedArray) {
        val titleResId =
            attributes.getResourceId(R.styleable.InputChipsCustomView_titleItem, 0)
        if (titleResId != 0) {
            hint = context.getString(titleResId)
        }
    }

    fun handleTitle(titleValue: String?) {
        binding.typeText.text = titleValue
    }

    fun selectedItem(status: Boolean) {
        mSelected = status
        if (status) {
            handleSelectedBackground()
        } else {
            handleNotSelectedBackground()
        }
    }

    private fun handleNotSelectedBackground() {
        binding.typeBackground.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.bg_input_chips_not_selected
            )
        )
        binding.typeText.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context,
                R.color.gray_scale_400
            )
        )
    }

    private fun handleSelectedBackground() {
        binding.typeBackground.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.bg_input_chips_selected
            )
        )
        binding.typeText.setTextColor(ContextCompat.getColor(context, R.color.white))
    }
}
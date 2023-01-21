package br.oficial.savestudents.ui_component.subtitleToggle

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import br.oficial.savestudents.R
import br.oficial.savestudents.databinding.SubtitleToggleBinding
import com.google.android.material.textfield.TextInputLayout

class SubtitleToggleCustomView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet?,
    defStyledAttr: Int = 0
) : TextInputLayout(context, attr, defStyledAttr) {
    private var title: String? = null
    private lateinit var backgroundBox: Drawable

    private val binding: SubtitleToggleBinding = SubtitleToggleBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        setLayout(attr)
        handleTitle(title)
        handleBackgroundBox(backgroundBox)
    }

    private fun setLayout(attr: AttributeSet?) {
        attr?.let { attributeSet: AttributeSet ->
            val attributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.SubtitleToggleCustomView)
            attributesTitle(attributes)
            attributesBackgroundBox(attributes)
            attributes.recycle()
        }
    }

    private fun attributesTitle(attributes: TypedArray) {
        val titleResId =
            attributes.getResourceId(R.styleable.SubtitleToggleCustomView_type, 0)
        if (titleResId != 0) {
            title = context.getString(titleResId)
        }
    }

    private fun attributesBackgroundBox(attributes: TypedArray) {
        val backgroundBoxResId =
            attributes.getResourceId(R.styleable.SubtitleToggleCustomView_background_box, 0)
        if (backgroundBoxResId != 0) {
            backgroundBox = ContextCompat.getDrawable(context, backgroundBoxResId)!!
        }
    }

    fun handleTitle(titleValue: String?) {
        binding.title.text = titleValue
    }

    private fun handleBackgroundBox(background: Drawable) {
        binding.box.setBackgroundDrawable(background)
    }
}
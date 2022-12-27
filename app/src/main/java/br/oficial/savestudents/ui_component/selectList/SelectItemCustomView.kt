package br.oficial.savestudents.ui_component.selectList

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import br.oficial.savestudents.R
import br.oficial.savestudents.databinding.SelectItemComponentBinding
import com.google.android.material.textfield.TextInputLayout


class SelectItemCustomView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet?,
    defStyledAttr: Int = 0
) : TextInputLayout(context, attr, defStyledAttr) {

    private var title: String? = null

    private val binding: SelectItemComponentBinding = SelectItemComponentBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        setLayout(attr)
        handleTitle(title)
    }

    private fun setLayout(attr: AttributeSet?) {
        attr?.let { attributeSet: AttributeSet ->
            val attributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.SelectItemCustomView)
            attributesTitle(attributes)
            attributes.recycle()
        }
    }

    private fun attributesTitle(attributes: TypedArray) {
        val titleResId =
            attributes.getResourceId(R.styleable.SelectItemCustomView_title, 0)
        if (titleResId != 0) {
            title = context.getString(titleResId)
        }
    }

    fun handleTitle(title: String?) {
        binding.title.text = title
    }

    fun view(): View {
        return binding.container
    }
}
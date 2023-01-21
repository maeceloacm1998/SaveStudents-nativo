package br.oficial.savestudents.ui_component.inputChips

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import br.oficial.savestudents.R
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

@EpoxyModelClass
abstract class InputChipsHolder : EpoxyModelWithHolder<InputChipsHolder.SectionHolder>() {

    @EpoxyAttribute
    var selected: Boolean = false

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickTimelineTypeListener: (typeSelected: String) -> Unit

    @EpoxyAttribute
    var marginTop: Int = 0

    @EpoxyAttribute
    var marginBottom: Int = 0

    @EpoxyAttribute
    var marginLeft: Int = 0

    @EpoxyAttribute
    var marginRight: Int = 0

    override fun bind(holder: SectionHolder) {
        super.bind(holder)

        holder.apply {
            handleTitle()
            handleClickTimelineType()
            selectedItem(mContainer.context, selected)
            setMargin()
        }
    }

    private fun SectionHolder.setMargin() {
        val params = (mContainer.layoutParams as ViewGroup.MarginLayoutParams).apply {
            setMargins(
                dpToPx(marginLeft),
                dpToPx(marginTop),
                dpToPx(marginRight),
                dpToPx(marginBottom)
            )
        }
        mContainer.layoutParams = params
    }

    private fun dpToPx(dp: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private fun SectionHolder.handleTitle() {
        mTypeText.text = title
    }

    private fun SectionHolder.handleClickTimelineType() {
        mContainer.setOnClickListener {
            clickTimelineTypeListener(title)
        }
    }

    private fun SectionHolder.selectedItem(context: Context, status: Boolean) {
        if (status) {
            handleSelectedBackground(context)
        } else {
            handleNotSelectedBackground(context)
        }
    }

    private fun SectionHolder.handleNotSelectedBackground(context: Context) {
        mContainer.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.bg_input_chips_not_selected
            )
        )
        mTypeText.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.gray_scale_400
            )
        )
    }

    private fun SectionHolder.handleSelectedBackground(context: Context) {
        mContainer.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.bg_input_chips_selected
            )
        )
        mTypeText.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.white
            )
        )
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mContainer: ConstraintLayout
        lateinit var mTypeText: TextView

        override fun bindView(itemView: View) {
            mContainer = itemView.findViewById(R.id.type_background)
            mTypeText = itemView.findViewById(R.id.type_text)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.input_chips
    }
}
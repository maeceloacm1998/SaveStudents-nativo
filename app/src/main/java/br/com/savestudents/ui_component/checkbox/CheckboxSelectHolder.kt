package br.com.savestudents.ui_component.checkbox

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import br.com.savestudents.R

@EpoxyModelClass
abstract class CheckboxSelectHolder : EpoxyModelWithHolder<CheckboxSelectHolder.SectionHolder>() {

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var check: (title: String) -> Unit

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var uncheck: (title: String) -> Unit

    @EpoxyAttribute
    var checked: Boolean = false

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
            setTitle()
            checkboxState()
            setMargin()
            setChecked()
        }
    }

    private fun SectionHolder.setTitle() {
        mTitle.text = title
    }

    private fun SectionHolder.checkboxState() {
        mCheckBox.setOnClickListener {
            if (mCheckBox.isChecked) {
                check(title)
            } else {
                uncheck(title)
            }
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

    private fun SectionHolder.setChecked() {
        mCheckBox.isChecked = checked
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mContainer: ConstraintLayout
        lateinit var mTitle: TextView
        lateinit var mCheckBox: CheckBox

        override fun bindView(itemView: View) {
            mContainer = itemView.findViewById(R.id.checkbox_container)
            mTitle = itemView.findViewById(R.id.title)
            mCheckBox = itemView.findViewById(R.id.checkbox)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.checkbox_select_holder
    }
}
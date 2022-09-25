package br.com.savestudents.ui_component.alert

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import br.com.savestudents.R

@EpoxyModelClass
abstract class AlertHolder : EpoxyModelWithHolder<AlertHolder.SectionHolder>() {
    @EpoxyAttribute
    lateinit var title: String

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
            setMargin()
        }
    }

    private fun SectionHolder.setTitle() {
        mTitle.text = title
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

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mContainer: View
        lateinit var mTitle: TextView

        override fun bindView(itemView: View) {
            mContainer = itemView.findViewById(R.id.alert_container)
            mTitle = itemView.findViewById(R.id.alert_title)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.alert_holder
    }
}
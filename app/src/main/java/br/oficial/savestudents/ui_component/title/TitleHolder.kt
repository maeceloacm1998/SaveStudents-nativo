package br.oficial.savestudents.ui_component.title

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import br.oficial.savestudents.R

@EpoxyModelClass
abstract class TitleHolder : EpoxyModelWithHolder<TitleHolder.SectionHolder>() {

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
        setTitle(holder)
        setMargin(holder)
    }

    private fun setMargin(holder: SectionHolder) {
        val params = (holder.mContainer.layoutParams as ViewGroup.MarginLayoutParams).apply {
            setMargins(
                dpToPx(marginLeft),
                dpToPx(marginTop),
                dpToPx(marginRight),
                dpToPx(marginBottom)
            )
        }
        holder.mContainer.layoutParams = params
    }

    private fun setTitle(holder: SectionHolder) {
        holder.mTitle.text = title
    }

    private fun dpToPx(dp: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mTitle: TextView
        lateinit var mContainer: ConstraintLayout

        override fun bindView(itemView: View) {
            mTitle = itemView.findViewById(R.id.title_holder)
            mContainer = itemView.findViewById(R.id.title_container)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.title_holder
    }
}
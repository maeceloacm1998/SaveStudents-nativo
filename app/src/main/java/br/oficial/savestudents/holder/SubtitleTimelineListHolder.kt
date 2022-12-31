package br.oficial.savestudents.holder

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import br.oficial.savestudents.R
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

@EpoxyModelClass
abstract class SubtitleTimelineListHolder :
    EpoxyModelWithHolder<SubtitleTimelineListHolder.SectionHolder>() {
    @EpoxyAttribute
    var marginTop: Int = 0

    @EpoxyAttribute
    var marginBottom: Int = 0

    @EpoxyAttribute
    var marginLeft: Int = 0

    @EpoxyAttribute
    var marginRight: Int = 0

    override fun bind(holder: SubtitleTimelineListHolder.SectionHolder) {
        super.bind(holder)
        holder.setMargin()
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
        lateinit var mContainer: ConstraintLayout

        override fun bindView(itemView: View) {
            mContainer = itemView.findViewById(R.id.container)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.subtitle_timeline_list_holder
    }
}
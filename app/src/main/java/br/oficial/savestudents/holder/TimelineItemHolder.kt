package br.oficial.savestudents.holder

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
abstract class TimelineItemHolder : EpoxyModelWithHolder<TimelineItemHolder.SectionHolder>() {

    @EpoxyAttribute
    lateinit var date: String

    @EpoxyAttribute
    lateinit var timelineName: String

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
            setDate()
            setTimelineName()
            setMargin()
        }
    }

    private fun SectionHolder.setDate() {
        mDate.text = date
    }


    private fun SectionHolder.setTimelineName() {
        mTimelineName.text = timelineName
    }

    private fun SectionHolder.setMargin() {
        val params = (mItemContainer.layoutParams as ViewGroup.MarginLayoutParams).apply {
            setMargins(
                dpToPx(marginLeft),
                dpToPx(marginTop),
                dpToPx(marginRight),
                dpToPx(marginBottom)
            )
        }
        mItemContainer.layoutParams = params
    }

    private fun dpToPx(dp: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mItemContainer: ConstraintLayout
        lateinit var mDate: TextView
        lateinit var mTimelineName: TextView

        override fun bindView(itemView: View) {
            mItemContainer = itemView.findViewById(R.id.timeline_item_container)
            mDate = itemView.findViewById(R.id.date)
            mTimelineName = itemView.findViewById(R.id.title)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.timeline_item_holder
    }
}
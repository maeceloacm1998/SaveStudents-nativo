package br.com.savestudents.holder

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import br.com.savestudents.R

@EpoxyModelClass
abstract class CalendarContainerHolder :
    EpoxyModelWithHolder<CalendarContainerHolder.SectionHolder>() {
    @EpoxyAttribute
    lateinit var initDate: String

    @EpoxyAttribute
    lateinit var finishDate: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickCalendarContainerListener: () -> Unit

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
//            setInitDate()
//            setFinishDate()
            setMargin()
            clickCalendarContainer()
        }
    }

    private fun SectionHolder.clickCalendarContainer() {
        mContainer.setOnClickListener {
            clickCalendarContainerListener()
        }
    }

    private fun SectionHolder.setInitDate() {
        mInitDate.text = initDate
    }

    private fun SectionHolder.setFinishDate() {
        mFinishDate.text = finishDate
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
        lateinit var mInitDate: TextView
        lateinit var mFinishDate: TextView

        override fun bindView(itemView: View) {
            mContainer = itemView.findViewById(R.id.calendar_container)
            mInitDate = itemView.findViewById(R.id.initial_date)
            mFinishDate = itemView.findViewById(R.id.finish_date)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.calendar_container_holder
    }
}
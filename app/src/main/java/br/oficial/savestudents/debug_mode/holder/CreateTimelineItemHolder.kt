package br.oficial.savestudents.debug_mode.holder

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import br.oficial.savestudents.R
import br.oficial.savestudents.model.CreateTimelineItem

@EpoxyModelClass
abstract class CreateTimelineItemHolder: EpoxyModelWithHolder<CreateTimelineItemHolder.SectionHolder>() {
    @EpoxyAttribute
    var mId: Int = 0

    @EpoxyAttribute
    lateinit var subjectName: String

    @EpoxyAttribute
    lateinit var date: String

    @EpoxyAttribute
    var timestampDate: Long = 0L

    @EpoxyAttribute
    lateinit var type: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickEditButtonListener: (timelineItem: CreateTimelineItem) -> Unit

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickDeleteButtonListener: (id: Int) -> Unit

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
            setSubjectName()
            editButton()
            deleteButton()
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

    private fun SectionHolder.setDate() {
        mDate.text = date
    }

    private fun SectionHolder.setSubjectName() {
        mSubjectName.text = subjectName
    }

    private fun SectionHolder.editButton() {
        mEditButton.setOnClickListener {
            val timelineItem = CreateTimelineItem(id = mId, subjectName = subjectName, date = timestampDate, type = type)
            clickEditButtonListener(timelineItem)
        }
    }

    private fun SectionHolder.deleteButton() {
        mDeleteButton.setOnClickListener {
            clickDeleteButtonListener(mId)
        }
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mContainer: ConstraintLayout
        lateinit var mDate: TextView
        lateinit var mSubjectName: TextView
        lateinit var mEditButton: ImageView
        lateinit var mDeleteButton: ImageView

        override fun bindView(itemView: View) {
            mContainer = itemView.findViewById(R.id.container)
            mDate = itemView.findViewById(R.id.date)
            mSubjectName = itemView.findViewById(R.id.subject_name)
            mEditButton = itemView.findViewById(R.id.edit_button)
            mDeleteButton = itemView.findViewById(R.id.delete_button)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.create_timeline_item_holder
    }
}
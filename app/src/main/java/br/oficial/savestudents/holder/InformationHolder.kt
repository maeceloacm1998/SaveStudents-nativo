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
abstract class InformationHolder : EpoxyModelWithHolder<InformationHolder.SectionHolder>() {

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var period: String

    @EpoxyAttribute
    lateinit var shift: String

    @EpoxyAttribute
    lateinit var subjectModel: String

    @EpoxyAttribute
    lateinit var teacher: String

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
            setPeriod()
            setShift()
            setTeacher()
            setSubjectModel()
            setMargin()
        }
    }

    private fun SectionHolder.setTitle() {
        mTitle.text = title
    }

    private fun SectionHolder.setPeriod() {
        mPeriod.text = period
    }

    private fun SectionHolder.setShift() {
        mShift.text = "Turno: $shift"
    }

    private fun SectionHolder.setTeacher() {
        mTeacher.text = "Docente: $teacher"
    }

    private fun SectionHolder.setSubjectModel() {
        mSubjectModel.text = "Modelo: $subjectModel"
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
        lateinit var mTitle: TextView
        lateinit var mPeriod: TextView
        lateinit var mShift: TextView
        lateinit var mTeacher: TextView
        lateinit var mSubjectModel: TextView

        override fun bindView(itemView: View) {
            mContainer = itemView.findViewById(R.id.information_timeline_container)
            mTitle = itemView.findViewById(R.id.title)
            mPeriod = itemView.findViewById(R.id.period)
            mShift = itemView.findViewById(R.id.shift)
            mTeacher = itemView.findViewById(R.id.teacher)
            mSubjectModel = itemView.findViewById(R.id.subject_model)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.information_timeline_holder
    }
}
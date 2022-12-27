package br.oficial.savestudents.debug_mode.holder

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import br.oficial.savestudents.R
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

@EpoxyModelClass
abstract class SubjectEditHorizontalCardHolder : EpoxyModelWithHolder<SubjectEditHorizontalCardHolder.SectionHolder>() {
    @EpoxyAttribute
    lateinit var mId: String

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var subTitle: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickEditOptionListener: (id: String) -> Unit

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickDeleteSubjectListener: (id: String) -> Unit

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
            setSubtitle()
            clickEditOption()
            clickDeleteSubject()
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

    private fun SectionHolder.setTitle() {
        mTitle.text = title
    }

    private fun SectionHolder.setSubtitle() {
        mSubtitle.text = subTitle
    }

    private fun SectionHolder.clickEditOption() {
        mEditOption.setOnClickListener {
            clickEditOptionListener(mId)
        }
    }

    private fun SectionHolder.clickDeleteSubject() {
        mDeleteSubject.setOnClickListener {
            clickDeleteSubjectListener(mId)
        }
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mContainer: ConstraintLayout
        lateinit var mTitle: TextView
        lateinit var mSubtitle: TextView
        lateinit var mEditOption: ImageView
        lateinit var mDeleteSubject: ImageView

        override fun bindView(itemView: View) {
            mContainer = itemView.findViewById(R.id.card_container)
            mTitle = itemView.findViewById(R.id.title)
            mSubtitle = itemView.findViewById(R.id.subtitle)
            mEditOption = itemView.findViewById(R.id.edit_option)
            mDeleteSubject = itemView.findViewById(R.id.delete_subject)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.subject_edit_horizontal_card_holder
    }
}
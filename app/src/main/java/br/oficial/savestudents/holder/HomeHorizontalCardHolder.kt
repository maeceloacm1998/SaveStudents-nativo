package br.oficial.savestudents.holder

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import br.oficial.savestudents.R

@EpoxyModelClass
abstract class HomeHorizontalCardHolder :
    EpoxyModelWithHolder<HomeHorizontalCardHolder.SectionHolder>() {

    @EpoxyAttribute
    var subjectId: String = ""

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var period: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickHorizontalCardListener: (subjectId: String) -> Unit

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
        setMargin(holder)
        setTitle(holder)
        setSubtitle(holder)
        handleCLickHorizontalCard(holder)
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

    private fun setSubtitle(holder: SectionHolder) {
        holder.mSubtitle.text = period
    }

    private fun handleCLickHorizontalCard(holder: SectionHolder) {
        holder.mContainer.setOnClickListener {
            clickHorizontalCardListener(subjectId)
        }
    }

    private fun dpToPx(dp: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mContainer: View
        lateinit var mTitle: TextView
        lateinit var mSubtitle: TextView

        override fun bindView(itemView: View) {
            mContainer = itemView.findViewById(R.id.card_container)
            mTitle = itemView.findViewById(R.id.title)
            mSubtitle = itemView.findViewById(R.id.subtitle)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.home_horizontal_card_holder
    }
}
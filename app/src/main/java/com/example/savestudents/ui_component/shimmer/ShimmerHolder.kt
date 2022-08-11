package com.example.savestudents.ui_component.shimmer

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.savestudents.R
import com.facebook.shimmer.ShimmerFrameLayout

@EpoxyModelClass
abstract class ShimmerHolder : EpoxyModelWithHolder<ShimmerHolder.SectionHolder>() {
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
            setMargin()
        }
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mContainer: ShimmerFrameLayout
        override fun bindView(itemView: View) {
            mContainer = itemView.findViewById(R.id.shimmer_holder)
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

    override fun getDefaultLayout(): Int {
        return R.layout.checkbox_radio_shimmer
    }
}
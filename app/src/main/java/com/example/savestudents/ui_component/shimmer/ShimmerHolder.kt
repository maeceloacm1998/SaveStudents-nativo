package com.example.savestudents.ui_component.shimmer

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.savestudents.R

@EpoxyModelClass
abstract class ShimmerHolder : EpoxyModelWithHolder<ShimmerHolder.SectionHolder>() {
    inner class SectionHolder : EpoxyHolder() {
        override fun bindView(itemView: View) {}
    }

    override fun getDefaultLayout(): Int {
        return R.layout.checkbox_radio_shimmer
    }
}
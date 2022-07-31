package com.example.savestudents.ui_component.search

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.savestudents.R

@EpoxyModelClass
abstract class SearchEditTextHolder: EpoxyModelWithHolder<SearchEditTextHolder.SectionHolder>() {

    override fun bind(holder: SectionHolder) {
        super.bind(holder)
    }

    inner class SectionHolder: EpoxyHolder() {
        override fun bindView(itemView: View) {

        }

    }

    override fun getDefaultLayout(): Int {
        return R.layout.search_bar_holder
    }
}
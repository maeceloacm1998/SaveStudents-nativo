package com.example.savestudents.ui_component.header

import android.view.View
import android.widget.ImageButton
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.savestudents.R

@EpoxyModelClass
abstract class HeaderHolder: EpoxyModelWithHolder<HeaderHolder.SectionHolder>() {

    override fun bind(holder: SectionHolder) {
        super.bind(holder)
    }

    inner class SectionHolder: EpoxyHolder() {
        lateinit var mMenuButton: ImageButton

        override fun bindView(itemView: View) {
            mMenuButton = itemView.findViewById(R.id.menu_button)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.header_home_holder
    }
}
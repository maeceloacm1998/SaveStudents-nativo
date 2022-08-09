package com.example.savestudents.holder

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.savestudents.R

@EpoxyModelClass
abstract class ResponseErrorFilterOptionsHolder :
    EpoxyModelWithHolder<ResponseErrorFilterOptionsHolder.SectionHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var tryAgainListener: () -> Unit

    override fun bind(holder: SectionHolder) {
        super.bind(holder)
        holder.apply {
            mButton.setOnClickListener {
                tryAgainListener()
            }
        }
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mButton: View
        override fun bindView(itemView: View) {
            mButton = itemView.findViewById(R.id.button)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.response_error_filter_options_holder
    }
}
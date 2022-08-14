package com.example.savestudents.holder

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.savestudents.R

@EpoxyModelClass
abstract class ResponseErrorTryAgainListenerHolder :
    EpoxyModelWithHolder<ResponseErrorTryAgainListenerHolder.SectionHolder>() {

    @EpoxyAttribute
    lateinit var message: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var tryAgainListener: () -> Unit

    override fun bind(holder: SectionHolder) {
        super.bind(holder)
        holder.apply {
            setMessage()
            setTryAgainListener()
        }
    }

    private fun SectionHolder.setMessage() {
        mMessage.text = message
    }

    private fun SectionHolder.setTryAgainListener() {
        mButton.setOnClickListener {
            tryAgainListener()
        }
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mButton: View
        lateinit var mMessage: TextView

        override fun bindView(itemView: View) {
            mMessage = itemView.findViewById(R.id.message)
            mButton = itemView.findViewById(R.id.button)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.response_error_try_again_listener_holder
    }
}
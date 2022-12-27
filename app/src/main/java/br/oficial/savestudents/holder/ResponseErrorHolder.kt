package br.oficial.savestudents.holder

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import br.oficial.savestudents.R

@EpoxyModelClass
abstract class ResponseErrorHolder : EpoxyModelWithHolder<ResponseErrorHolder.SectionHolder>() {
    @EpoxyAttribute
    lateinit var message: String

    @EpoxyAttribute
    lateinit var description: String

    override fun bind(holder: SectionHolder) {
        super.bind(holder)
        holder.apply {
            setMessage()
            setDescription()
        }
    }

    private fun SectionHolder.setMessage() {
        mMessage.text = message
    }

    private fun SectionHolder.setDescription() {
        mDescription.text = description
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mMessage: TextView
        lateinit var mDescription: TextView

        override fun bindView(itemView: View) {
            mMessage = itemView.findViewById(R.id.message)
            mDescription = itemView.findViewById(R.id.description)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.response_error_holder
    }
}
package com.example.savestudents.holder

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.savestudents.R

@EpoxyModelClass
abstract class TimelineItemHolder : EpoxyModelWithHolder<TimelineItemHolder.SectionHolder>() {

    @EpoxyAttribute
    open var isNotificationActivated: Boolean = false

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
            backgroundTransparent(mItemContainer.context)
            clickNotificationButton()
            setMargin()
        }
    }

    private fun SectionHolder.clickNotificationButton() {
        mContainerNotification.setOnClickListener {
            if (!isNotificationActivated) {
                backgroundTransparent(it.context)
            } else {
                backgroundActivated(it.context)
            }
        }
    }

    private fun SectionHolder.backgroundTransparent(context: Context) {
        mContainerNotification.setBackgroundDrawable(context.getDrawable(R.drawable.bg_transparent_with_border_primary))
        mIconNotification.setColorFilter(ContextCompat.getColor(context, R.color.primary))
        mIconNotification.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_notifications_off_17))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun SectionHolder.backgroundActivated(context: Context) {
        mContainerNotification.setBackgroundDrawable(context.getDrawable(R.drawable.bg_rounded_primary))
        mIconNotification.setColorFilter(ContextCompat.getColor(context, R.color.white))
        mIconNotification.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_notifications_active_17))
    }

    private fun SectionHolder.setMargin() {
        val params = (mItemContainer.layoutParams as ViewGroup.MarginLayoutParams).apply {
            setMargins(
                dpToPx(marginLeft),
                dpToPx(marginTop),
                dpToPx(marginRight),
                dpToPx(marginBottom)
            )
        }
        mItemContainer.layoutParams = params
    }

    private fun dpToPx(dp: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mItemContainer: ConstraintLayout
        lateinit var mContainerNotification: View
        lateinit var mIconNotification: ImageView

        override fun bindView(itemView: View) {
            mItemContainer = itemView.findViewById(R.id.timeline_item_container)
            mContainerNotification = itemView.findViewById(R.id.alert_button_container)
            mIconNotification = itemView.findViewById(R.id.alert_button_icon)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.timeline_item_holder
    }
}
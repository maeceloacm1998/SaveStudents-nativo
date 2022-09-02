package com.example.savestudents.holder

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.savestudents.R

@EpoxyModelClass
abstract class InformationHolder : EpoxyModelWithHolder<InformationHolder.SectionHolder>() {

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var period: String

    @EpoxyAttribute
    lateinit var shift: String

    @EpoxyAttribute
    lateinit var teacher: String

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
            setTitle()
            setPeriod()
            setShift()
            setTeacher()
            setMargin()
            backgroundTransparent(mContainer.context)
            clickNotificationButton()
        }
    }

    private fun SectionHolder.setTitle() {
        mTitle.text = title
    }

    private fun SectionHolder.setPeriod() {
        mPeriod.text = period
    }

    private fun SectionHolder.setShift() {
        mShift.text = shift
    }

    private fun SectionHolder.setTeacher() {
        mTeacher.text = teacher
    }

    private fun SectionHolder.clickNotificationButton() {
        mNotificationButton.setOnClickListener {
            if (!isNotificationActivated) {
                backgroundTransparent(it.context)
            } else {
                backgroundActivated(it.context)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun SectionHolder.backgroundTransparent(context: Context) {
        mNotificationButton.setBackgroundDrawable(context.getDrawable(R.drawable.bg_transparent_with_border_primary))
        mNotificationText.setTextColor(ContextCompat.getColor(context, R.color.primary))
        mNotificationIcon.setColorFilter(ContextCompat.getColor(context, R.color.primary))
        mNotificationIcon.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_notifications_off_17))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun SectionHolder.backgroundActivated(context: Context) {
        mNotificationButton.setBackgroundDrawable(context.getDrawable(R.drawable.bg_rounded_primary))
        mNotificationText.setTextColor(ContextCompat.getColor(context, R.color.white))
        mNotificationIcon.setColorFilter(ContextCompat.getColor(context, R.color.white))
        mNotificationIcon.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_notifications_active_17))
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

    inner class SectionHolder : EpoxyHolder() {
        lateinit var mContainer: ConstraintLayout
        lateinit var mTitle: TextView
        lateinit var mPeriod: TextView
        lateinit var mShift: TextView
        lateinit var mTeacher: TextView
        lateinit var mNotificationButton: View
        lateinit var mNotificationIcon: ImageView
        lateinit var mNotificationText: TextView

        override fun bindView(itemView: View) {
            mContainer = itemView.findViewById(R.id.information_timeline_container)
            mTitle = itemView.findViewById(R.id.title)
            mPeriod = itemView.findViewById(R.id.period)
            mShift = itemView.findViewById(R.id.shift)
            mTeacher = itemView.findViewById(R.id.teacher)
            mNotificationButton = itemView.findViewById(R.id.notification_timeline_button)
            mNotificationIcon = itemView.findViewById(R.id.notification_icon)
            mNotificationText = itemView.findViewById(R.id.notification_text)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.information_timeline_holder
    }
}
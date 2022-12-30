package br.oficial.savestudents.controller

import br.oficial.savestudents.R
import br.oficial.savestudents.constants.CreateTimelineConstants
import br.oficial.savestudents.helper.TimelineItemType.*
import br.oficial.savestudents.helper.TimelineItemTypeColorHelper
import br.oficial.savestudents.holder.timelineItemHolder
import br.oficial.savestudents.model.TimelineItem
import br.oficial.savestudents.ui_component.shimmer.shimmerHolder
import com.airbnb.epoxy.EpoxyController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimelineController : EpoxyController() {
    private var timelineList: TimelineItem = TimelineItem()

    override fun buildModels() {
        handleListItems()
    }

    private fun handleListItems() {
        if (timelineList.timelineList.isNullOrEmpty()) {
            shimmerHolder {
                id("shimmer_item")
                layout(R.layout.timeline_item_shimmer)
                marginTop(16)
                marginBottom(16)
                marginLeft(16)
                marginRight(16)
            }
        } else {
            timelineList.timelineList?.forEach { item ->
                timelineItemHolder {
                    id(item.id)
                    timelineName(item.subjectName)
                    date(formatDate(item.date))
                    backgroundType(getBackgroundType(item.type))
                    marginTop(6)
                    marginBottom(6)
                    marginLeft(16)
                    marginRight(16)
                }
            }
        }
    }

    private fun getBackgroundType(type: String): TimelineItemTypeColorHelper {
        when (type) {
            CLASS.type -> {
                return TimelineItemTypeColorHelper.CLASS
            }

            EXAM.type -> {
                return TimelineItemTypeColorHelper.EXAM
            }

            HOLIDAY.type -> {
                return TimelineItemTypeColorHelper.HOLIDAY
            }
        }

        return TimelineItemTypeColorHelper.CLASS
    }

    private fun formatDate(timestamp: Long): String {
        val pattern = SimpleDateFormat(
            CreateTimelineConstants.FormatDate.DAY_AND_MONTH_DATE,
            Locale.getDefault()
        )
        return pattern.format(Date(timestamp))
    }

    fun setTimelineList(timelineList: TimelineItem) {
        this.timelineList = timelineList
        requestModelBuild()
    }
}
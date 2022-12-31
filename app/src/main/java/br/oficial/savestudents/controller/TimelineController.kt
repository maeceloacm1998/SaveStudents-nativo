package br.oficial.savestudents.controller

import br.oficial.savestudents.R
import br.oficial.savestudents.helper.TimelineItemType.*
import br.oficial.savestudents.helper.TimelineItemTypeColorHelper
import br.oficial.savestudents.holder.timelineItemHolder
import br.oficial.savestudents.model.TimelineItem
import br.oficial.savestudents.ui_component.shimmer.shimmerHolder
import br.oficial.savestudents.utils.DateUtils
import com.airbnb.epoxy.EpoxyController

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
                    date(DateUtils.formatDate(item.date, DateUtils.DAY_AND_MONTH_DATE))
                    backgroundType(handleBackground(item.type, item.date))
                    marginTop(6)
                    marginBottom(6)
                    marginLeft(16)
                    marginRight(16)
                }
            }
        }
    }

    private fun handleBackground(type: String, date: Long): TimelineItemTypeColorHelper {
        if (isCurrentDay(date)) {
            return TimelineItemTypeColorHelper.CURRENT_DAY
        }

        return getBackgroundType(type)
    }

    private fun isCurrentDay(date: Long): Boolean {
        val currentDay = DateUtils.getCurrentDay()
        return DateUtils.formatDate(
            currentDay, DateUtils.DAY_AND_MONTH_DATE
        ) == DateUtils.formatDate(date, DateUtils.DAY_AND_MONTH_DATE)
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

    fun setTimelineList(timelineList: TimelineItem) {
        this.timelineList = timelineList
        requestModelBuild()
    }
}
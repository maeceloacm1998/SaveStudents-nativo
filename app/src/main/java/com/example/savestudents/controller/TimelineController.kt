package com.example.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import com.example.savestudents.R
import com.example.savestudents.constants.CreateTimelineConstants
import com.example.savestudents.holder.informationHolder
import com.example.savestudents.holder.timelineItemHolder
import com.example.savestudents.model.TimelineItem
import com.example.savestudents.model.contract.TimelineContract
import com.example.savestudents.ui_component.separator.separatorHolder
import com.example.savestudents.ui_component.shimmer.shimmerHolder
import com.example.savestudents.ui_component.title.titleHolder
import com.example.savestudents.view.fragment.CreateTimelineItemDialog
import java.text.SimpleDateFormat
import java.util.*

class TimelineController(private val mContract: TimelineContract) : EpoxyController() {
    private var timelineList: TimelineItem = TimelineItem()
    private var isLoading = false

    override fun buildModels() {
        handleTopItems()
        handleListItems()
    }

    private fun handleTopItems() {
        handleInformationHolder()
        handleSeparator()
    }

    private fun handleInformationHolder() {
        if (isLoading) {
            shimmerHolder {
                id("information_shimmer")
                layout(R.layout.information_timeline_shimmer)
                marginTop(10)
                marginRight(24)
                marginLeft(24)
            }
        } else {
            if(timelineList.subjectsInformation != null) {
                informationHolder {
                    id(timelineList.subjectsInformation?.id)
                    title(timelineList.subjectsInformation?.subjectName)
                    period(timelineList.subjectsInformation?.period)
                    shift(timelineList.subjectsInformation?.shift)
                    teacher(timelineList.subjectsInformation?.teacherName)
                    marginTop(10)
                    marginRight(24)
                    marginLeft(24)
                }
            }
        }
    }

    private fun handleSeparator() {
        separatorHolder {
            id("separator")
            marginTop(24)
            marginLeft(16)
            marginRight(16)
        }
    }

    private fun handleListItems() {
        titleHolder {
            id("list_title")
            title("Cronograma")
            marginTop(24)
            marginLeft(16)
            marginRight(16)
        }

        if (isLoading) {
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
                    marginTop(6)
                    marginBottom(6)
                    marginLeft(16)
                    marginRight(16)
                }
            }
        }
    }

    private fun formatDate(timestamp: Long): String {
        val pattern = SimpleDateFormat(CreateTimelineConstants.FormatDate.DAY_AND_MONTH_DATE, Locale.getDefault())
        return pattern.format(Date(timestamp))
    }

    fun setLoading(status: Boolean) {
        isLoading = status
        requestModelBuild()
    }

    fun setTimelineList(timelineList: TimelineItem) {
        this.timelineList = timelineList
        requestModelBuild()
    }
}
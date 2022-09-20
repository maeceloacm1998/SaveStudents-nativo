package com.example.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import com.example.savestudents.model.CreateTimelineItem
import com.example.savestudents.model.contract.CreateTimelineContract
import com.example.savestudents.ui_component.title.titleHolder

class CreateTimelineController(private val mContract: CreateTimelineContract) : EpoxyController() {
    private var timelineItemsList: MutableList<CreateTimelineItem> = mutableListOf()

    override fun buildModels() {
        timelineItemsList.forEach { item ->
            titleHolder {
                id("title + $item.id")
                title(item.subjectName)
            }
        }
    }

    fun setTimelineItemsList(timelineItemsList: MutableList<CreateTimelineItem>) {
        this.timelineItemsList = timelineItemsList
        requestModelBuild()
    }
}
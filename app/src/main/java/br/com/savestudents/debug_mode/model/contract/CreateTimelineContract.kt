package br.com.savestudents.debug_mode.model.contract

import br.com.savestudents.model.CreateTimelineItem

interface CreateTimelineContract {
    fun clickEditButtonListener(timelineItem: CreateTimelineItem)
    fun clickDeleteButtonListener(id: Int)
}
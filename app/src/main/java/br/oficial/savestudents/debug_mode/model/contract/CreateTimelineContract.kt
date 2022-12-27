package br.oficial.savestudents.debug_mode.model.contract

import br.oficial.savestudents.model.CreateTimelineItem

interface CreateTimelineContract {
    fun clickEditButtonListener(timelineItem: CreateTimelineItem)
    fun clickDeleteButtonListener(id: Int)
}
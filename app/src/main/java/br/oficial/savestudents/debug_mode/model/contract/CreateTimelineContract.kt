package br.oficial.savestudents.debug_mode.model.contract

import com.example.data_transfer.model.CreateTimelineItem

interface CreateTimelineContract {
    fun clickEditButtonListener(timelineItem: CreateTimelineItem)
    fun clickDeleteButtonListener(id: Int)
}
package br.oficial.savestudents.debug_mode.model.contract

import com.example.data_transfer.model.entity.CreateTimelineItemEntity

interface EditTimelineItemDialogContract {
    fun editTimelineItemListener(timelineItem: CreateTimelineItemEntity)
}
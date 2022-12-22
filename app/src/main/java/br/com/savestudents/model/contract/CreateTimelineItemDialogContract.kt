package br.com.savestudents.model.contract

import br.com.savestudents.service.internal.entity.CreateTimelineItemEntity

interface CreateTimelineItemDialogContract {
    fun createTimelineItemListener(timelineItem: CreateTimelineItemEntity)
}
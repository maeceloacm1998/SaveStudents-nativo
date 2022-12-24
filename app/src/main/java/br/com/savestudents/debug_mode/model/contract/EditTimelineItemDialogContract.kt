package br.com.savestudents.debug_mode.model.contract

import br.com.savestudents.service.internal.entity.CreateTimelineItemEntity

interface EditTimelineItemDialogContract {
    fun editTimelineItemListener(timelineItem: CreateTimelineItemEntity)
}
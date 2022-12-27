package br.oficial.savestudents.debug_mode.model.contract

import br.oficial.savestudents.service.internal.entity.CreateTimelineItemEntity

interface CreateTimelineItemDialogContract {
    fun createTimelineItemListener(timelineItem: CreateTimelineItemEntity)
}
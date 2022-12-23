package br.com.savestudents.model

import br.com.savestudents.service.internal.entity.CreateTimelineItemEntity

data class CreateTimelineItem(
    val id: Int = 0,
    var date: Long = 0L,
    var subjectName: String = ""
)

fun CreateTimelineItemEntity.asDomainModel(): CreateTimelineItem {
    return CreateTimelineItem(
        id = this.id,
        date = this.date,
        subjectName = this.subjectName
    )
}
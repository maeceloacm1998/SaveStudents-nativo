package com.example.data_transfer.model

import com.example.data_transfer.model.entity.CreateTimelineItemEntity

data class CreateTimelineItem(
    val id: Int = 0,
    var date: Long = 0L,
    var subjectName: String = "",
    var type: String = ""
)

fun CreateTimelineItemEntity.asDomainModel(): CreateTimelineItem {
    return CreateTimelineItem(
        id = this.id,
        date = this.date,
        subjectName = this.subjectName,
        type = this.type
    )
}
package com.example.data_transfer.model

import com.example.data_transfer.dto.TimelineTypeDTO

data class TimelineType(
    val id: String?,
    val type: String?
)

fun TimelineTypeDTO.asDomainModel() =
    TimelineType(
        id = this.id,
        type = this.type
    )
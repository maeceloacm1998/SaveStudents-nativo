package br.oficial.savestudents.model

import br.oficial.savestudents.dto.TimelineTypeDTO

data class TimelineType(
    val id: String?,
    val type: String?
)

fun TimelineTypeDTO.asDomainModel() =
    TimelineType(
        id = this.id,
        type = this.type
    )
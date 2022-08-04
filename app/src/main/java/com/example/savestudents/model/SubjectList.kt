package com.example.savestudents.model

import com.example.savestudents.dto.SubjectListDto

data class SubjectList(
    var id: Long? = 0,
    var title: String? = "",
    var period: Long? = 0
)

fun SubjectListDto.asDomainModel() {
    SubjectList(
        this.id,this.title, this.period
    )
}
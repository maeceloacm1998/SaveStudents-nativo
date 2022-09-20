package com.example.savestudents.dto

import com.example.savestudents.model.SubjectList

data class SubjectListDto(
    var id: String = "",
    var subjectName: String = "",
    var teacherName: String = "",
    var period: String = "",
    var shift: String = ""
)

fun List<SubjectListDto>.asDomainModel(): List<SubjectList> {
    return this.map {
        SubjectList(
            it.id, it.subjectName, it.teacherName, it.period, it.shift
        )
    }
}
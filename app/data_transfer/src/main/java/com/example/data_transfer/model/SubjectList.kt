package com.example.data_transfer.model

import com.example.data_transfer.dto.SubjectListDto

data class SubjectList(
    var id: String = "",
    var deeplink: String = "",
    var subjectName: String = "",
    var teacherName: String = "",
    var period: String = "",
    var shift: String = "",
)

fun SubjectListDto.asModel(): SubjectList =
    SubjectList(
        id = id,
        deeplink = deeplink,
        subjectName = subjectName,
        teacherName = teacherName,
        period = period,
        shift = shift
    )

fun List<SubjectListDto>.asDomainModel(): List<SubjectList> {
    return this.map {
        SubjectList(
            id = it.id,
            deeplink = it.deeplink,
            subjectName = it.subjectName,
            teacherName = it.teacherName,
            period = it.period,
            shift = it.shift
        )
    }
}
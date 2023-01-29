package com.example.data_transfer.dto

import com.example.data_transfer.model.SubjectList

data class SubjectListDto(
    var id: String = "",
    var deeplink: String = "",
    var subjectName: String = "",
    var teacherName: String = "",
    var period: String = "",
    var shift: String = ""
)
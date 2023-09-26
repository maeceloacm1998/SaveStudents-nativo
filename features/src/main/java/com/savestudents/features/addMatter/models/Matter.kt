package com.savestudents.features.addMatter.models

data class Matter(
    val id: String = "",
    val period: String = "",
    val matterName: String = "",
    val teacherName: String = "",
    val timelineList: List<Timeline> = mutableListOf()
) {
    data class Timeline(
        val date: Long = 0L,
        val id: Int = 0,
        val subjectName: String = "",
        val type: String = ""
    )
}
package com.example.savestudents.dto

import com.example.savestudents.model.InformationModel
import com.example.savestudents.model.SubjectList

data class InformationDTO(
    val name: String = "",
    val items: List<SubjectList> = mutableListOf()
)

fun List<InformationDTO>.asDomainModel(): List<InformationModel> {
    return this.map { item ->
        InformationModel(
            item.name,
            item.items
        )
    }
}
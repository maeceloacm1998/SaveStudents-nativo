package com.example.data_transfer.dto

import com.example.data_transfer.model.FilterOption

data class FilterOptionDTO(
    val id: String = "",
    val name: String = ""
)

fun List<FilterOptionDTO>.asDomainModel(): List<FilterOption> {
    return this.map { item ->
        FilterOption(item.id, item.name)
    }
}
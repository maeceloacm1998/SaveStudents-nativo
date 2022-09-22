package br.com.savestudents.dto

import br.com.savestudents.model.FilterOption

data class FilterOptionDTO(
    val id: String = "",
    val name: String = ""
)

fun List<FilterOptionDTO>.asDomainModel(): List<FilterOption> {
    return this.map { item ->
        FilterOption(item.id, item.name)
    }
}
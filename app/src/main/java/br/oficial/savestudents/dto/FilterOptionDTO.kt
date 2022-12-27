package br.oficial.savestudents.dto

import br.oficial.savestudents.model.FilterOption

data class FilterOptionDTO(
    val id: String = "",
    val name: String = ""
)

fun List<FilterOptionDTO>.asDomainModel(): List<FilterOption> {
    return this.map { item ->
        FilterOption(item.id, item.name)
    }
}
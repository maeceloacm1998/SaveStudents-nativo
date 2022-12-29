package br.oficial.savestudents.helper

import br.oficial.savestudents.R

enum class TimelineItemTypeColorHelper(val textColor: Int, val drawable: Int) {
    CLASS(R.color.soft_black, R.drawable.bg_rounded_ghost_white),
    EXAM(R.color.white, R.drawable.bg_rounded_red),
    HOLIDAY(R.color.white, R.drawable.bg_rounded_green),
    CURRENT_DAY(R.color.white, R.drawable.bg_rounded_primary)
}

enum class TimelineItemType(val type: String) {
    CLASS("Matéria"),
    EXAM("Prova"),
    HOLIDAY("Feriado"),
}
package br.oficial.savestudents.helper

import br.oficial.savestudents.R

enum class TimelineItemTypeColorHelper(val textColor: Int, val drawable: Int, val typePointer: Int) {
    CLASS(R.color.soft_black, R.drawable.bg_rounded_ghost_white, R.color.primary),
    EXAM(R.color.white, R.drawable.bg_rounded_red, R.color.primary),
    HOLIDAY(R.color.white, R.drawable.bg_rounded_green, R.color.primary),
}

enum class TimelineItemType(val type: String) {
    CLASS("Matéria"),
    EXAM("Prova"),
    HOLIDAY("Feriado"),
}
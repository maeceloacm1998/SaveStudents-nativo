package com.savestudents.core.utils

enum class DaysType(val value: String) {
    MONDAY("Segunda"),
    TUESDAY("Terça"),
    WEDNESDAY("Quarta"),
    THURSDAY("Quinta"),
    FRIDAY("Sexta"),
    SATURDAY("Sabado"),
    SUNDAY("Domingo");

    companion object {
        fun getDayOfWeekList(): List<String> {
            return listOf(
                MONDAY.value,
                TUESDAY.value,
                WEDNESDAY.value,
                THURSDAY.value,
                FRIDAY.value,
                SATURDAY.value,
                SUNDAY.value
            )
        }
    }
}
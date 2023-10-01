package com.savestudents.core.utils

import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Locale

object DateUtils {

    /**
     * Função para formatar horas e minutos com algarismo unico.
     * Ex: 9:00 para 09:00
     * @param hour
     * @param minutes
     */
    fun formatTime(hour: String, minutes: String): String {
        val hour = hour.toInt()
        val minute = minutes.toInt()

        val formattedHour = String.format("%02d", hour)
        val formattedMinutes = String.format("%02d", minute)

        return "$formattedHour:$formattedMinutes"
    }

    /**
     * Função para pegar o dia, mês e ano do dia atual.
     *
     * Ex: val (day, month, year) = DateUtils.getCurrentDate()
     * @return Triple(day, month, year)
     */
    fun getCurrentDate(): Triple<Int, Int, Int> {
        val calendar = Calendar.getInstance()
        return Triple(
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.YEAR)
        )
    }

    /**
     * Retorna o nome em Português do mês passado por parâmetro
     * @param month
     */
    fun getMonthName(month: Int): String {
        val locale = Locale("pt", "BR")
        val symbols = DateFormatSymbols(locale)

        return if (month in 1..12) {
            symbols.months[month]
        } else {
            "Mês inválido"
        }
    }

    /**
     * Retorna data no formato dd/MM/YYYY
     * @param day
     * @param month
     * @param year
     */
    fun formatDate(day: Int, month: Int, year: Int): String {
        return "$day/$month/$year"
    }

    /**
     * Pegar todos os dias do ano que caem no dia da semana
     *
     *
     * Para usar, basta passar por parametros algum desses nomes da semana, sendo eles:
     *
     * Segunda, Terça, Quarta, Quinta e Sexta.
     * @param weekName
     */
    fun getWeeksList(weekName: String): List<Triple<Int, Int, Int>> {
        return when (weekName) {
            DaysType.MONDAY.value -> getDataListPerWeek(WeekType.MONDAY)
            DaysType.TUESDAY.value -> getDataListPerWeek(WeekType.TUESDAY)
            DaysType.WEDNESDAY.value -> getDataListPerWeek(WeekType.WEDNESDAY)
            DaysType.THURSDAY.value -> getDataListPerWeek(WeekType.THURSDAY)
            DaysType.FRIDAY.value -> getDataListPerWeek(WeekType.FRIDAY)
            else -> getDataListPerWeek(WeekType.SATURDAY)
        }
    }

    private fun getDataListPerWeek(week: WeekType): MutableList<Triple<Int, Int, Int>> {
        val weeks: MutableList<Triple<Int, Int, Int>> = mutableListOf()

        val calendar = Calendar.getInstance()
        calendar.isLenient = false
        calendar.set(Calendar.YEAR, 2023)
        calendar.set(Calendar.MONTH, Calendar.JANUARY)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        while (calendar[Calendar.YEAR] == 2023) {
            if (calendar[Calendar.DAY_OF_WEEK] == week.value) {
                weeks.add(
                    Triple(
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR)
                    )
                )
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return weeks
    }
}
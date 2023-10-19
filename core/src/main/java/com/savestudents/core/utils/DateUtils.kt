package com.savestudents.core.utils

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    const val DATE_WITH_MONTH_NAME = "dd 'de' MMMM"
    const val NORMAL_DATE = "dd/MM/YYYY"

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

    /**
     * Formata a data de acordo com o pattern fornecido
     * @param pattern Ex: "dd/MM/YYYY"
     * @param timestamp
     */
    fun formatDateWithPattern(pattern: String, timestamp: Long): String {
        val dateFormat = SimpleDateFormat(pattern, Locale("pt", "BR"))
        return dateFormat.format(Date(addOneDayToTimestamp(timestamp)))
    }

    /**
     * Adiciona um dia no timestemp
     * Essa funcao foi criada porque o calendario do datePicker retornava timestemp com 1 dia atrasado
     * @param timestamp
     */
    fun addOneDayToTimestamp(timestamp: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        return calendar.timeInMillis
    }

    /**
     * Pega o nome do dia de semana pelo timestamp
     * @param timestamp
     */

    fun getDayOfWeekFromTimestamp(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            WeekType.MONDAY.value -> DaysType.MONDAY.value
            WeekType.TUESDAY.value -> DaysType.TUESDAY.value
            WeekType.WEDNESDAY.value -> DaysType.WEDNESDAY.value
            WeekType.THURSDAY.value -> DaysType.THURSDAY.value
            WeekType.FRIDAY.value -> DaysType.FRIDAY.value
            else -> DaysType.SATURDAY.value
        }
    }

    /**
     * Traz as datas da semana de acordo com o dia atual.
     * @return List<dd/MM/YYYY> data formatada.
     */
    fun getWeekDatesWithNormalFormat(): List<String> {
        val weekDates = mutableListOf<String>()
        val (day, month, year) = getCurrentDate()

        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)


        for (i in 1..7) {
            weekDates.add(formatDateWithPattern(NORMAL_DATE, calendar.timeInMillis))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return weekDates
    }


    /**
     * Traz as datas da semana de acordo com o dia atual.
     * @return List<timeInMillis> timestamp
     */
    fun getWeekDatesTimestamp(): List<Long> {
        val weekDates = mutableListOf<Long>()
        val (day, month, year) = getCurrentDate()

        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)


        for (i in 1..7) {
            weekDates.add(calendar.timeInMillis)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return weekDates
    }

    /**
     * Pega a data correspondente com o timestamp
     * @return Triple<year, month, day>
     */
    fun getDateWithTimestamp(timestamp: Long): Triple<Int, Int, Int> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return Triple(year, month, day)
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
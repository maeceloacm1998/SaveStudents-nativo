package com.savestudents.core.utils

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    private const val DAY_AND_MONTH_DATE = "dd/MM"

    fun formatTime(hour: String, minutes: String): String {
        val hour = hour.toInt()
        val minute = minutes.toInt()

        val formattedHour = String.format("%02d", hour)
        val formattedMinutes = String.format("%02d", minute)

        return "$formattedHour:$formattedMinutes"
    }

    fun getCurrentDay(): Long {
        return Calendar.getInstance().timeInMillis
    }

    fun formatDate(timestamp: Long, patternType: String): String {
        val pattern = SimpleDateFormat(
            patternType,
            Locale.getDefault()
        )
        return pattern.format(Date(timestamp))
    }

    fun getMonthName(month: Int): String {
        val locale = Locale("pt", "BR")
        val symbols = DateFormatSymbols(locale)

        return if (month in 1..12) {
            symbols.months[month]
        } else {
            "Mês inválido"
        }
    }

    fun isCurrentDate(dateToCompare: Long): Boolean {
        val timestampCurrentDay =
            formatDate(getCurrentDay(), DAY_AND_MONTH_DATE)

        val date = formatDate(dateToCompare, DAY_AND_MONTH_DATE)

        return date == timestampCurrentDay
    }

    fun formatDate(day: Int, month: Int, year: Int): String {
        return "$day/$month/$year"
    }

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
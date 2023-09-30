package com.savestudents.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.Exception
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {
    private const val DAY_AND_MONTH_DATE = "dd/MM"

    @RequiresApi(Build.VERSION_CODES.O)
    fun transformStringInDate(date: String): Date? {
        try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val dataLocalDate = LocalDate.parse(date, formatter)
            return Date.from(dataLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        } catch (error: Exception) {
            throw RuntimeException("Erro ao analisar a data: ${error.message}", error)
        }
    }

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

    fun getTimestamp(day: Int, month: Int, year: Int): Long {
        val cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, day)
        return cal.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun timestampsEquals(timestamp1: Long, timestamp2: Long): Boolean {
        val data1 = Instant.ofEpochMilli(timestamp1)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val data2 = Instant.ofEpochMilli(timestamp2)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        return when (data1.compareTo(data2)) {
            0 -> true
            else -> false
        }
    }

    fun extractYearMonthDay(timestamp: Long): Triple<Int, Int, Int> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp

        return Triple(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    fun getMonthName(month: Int): String {
        return if (month in 1..12) {
            DateFormatSymbols().months[month]
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
}
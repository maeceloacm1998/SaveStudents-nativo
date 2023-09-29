package com.savestudents.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

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
    fun isCurrentDate(dateToCompare: Long): Boolean {
        val timestampCurrentDay =
            formatDate(getCurrentDay(), DAY_AND_MONTH_DATE)

        val date = formatDate(dateToCompare, DAY_AND_MONTH_DATE)

        return date == timestampCurrentDay
    }
}
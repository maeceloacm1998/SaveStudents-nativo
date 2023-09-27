package com.savestudents.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.Exception
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

object DateUtils {
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
}
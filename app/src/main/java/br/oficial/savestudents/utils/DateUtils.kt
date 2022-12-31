package br.oficial.savestudents.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale.getDefault

class DateUtils {
    companion object {
        const val DAY_AND_MONTH_DATE = "dd/MM"

        fun getCurrentDay(): Long {
            return Calendar.getInstance().timeInMillis
        }

        fun formatDate(timestamp: Long, patternType: String): String {
            val pattern = SimpleDateFormat(
                patternType,
                getDefault()
            )
            return pattern.format(Date(timestamp))
        }
    }
}
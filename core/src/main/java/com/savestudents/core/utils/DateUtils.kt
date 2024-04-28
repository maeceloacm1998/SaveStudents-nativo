package com.savestudents.core.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
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
     * Função para pegar o timestamp do dia atual
     *
     * @return timeInMillis
     */
    fun getTimestampCurrentDate(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis
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
     * @param dayOfWeek
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeeksList(dayOfWeek: String): List<Long> {
        return when (dayOfWeek) {
            DaysType.MONDAY.value -> getDaysOfWeekTimestamps(DaysType.MONDAY.toString())
            DaysType.TUESDAY.value -> getDaysOfWeekTimestamps(DaysType.TUESDAY.toString())
            DaysType.WEDNESDAY.value -> getDaysOfWeekTimestamps(DaysType.WEDNESDAY.toString())
            DaysType.THURSDAY.value -> getDaysOfWeekTimestamps(DaysType.THURSDAY.toString())
            DaysType.FRIDAY.value -> getDaysOfWeekTimestamps(DaysType.FRIDAY.toString())
            DaysType.SATURDAY.value -> getDaysOfWeekTimestamps(DaysType.SATURDAY.toString())
            else -> getDaysOfWeekTimestamps(DaysType.SUNDAY.toString())
        }
    }

    /**
     * Formata a data de acordo com o pattern fornecido
     * @param pattern Ex: "dd/MM/YYYY"
     * @param timestamp
     */
    fun formatDateWithPattern(pattern: String, timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val dateFormat = SimpleDateFormat(pattern, Locale("pt", "BR"))
        return dateFormat.format(calendar.time)
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
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

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
            WeekType.SATURDAY.value -> DaysType.SATURDAY.value
            else -> DaysType.SUNDAY.value
        }
    }

    /**
     * Pegar a data em com dia, mes e ano e transformar em timestamp
     * @param day Int
     * @param month Int
     * @param year Int
     * @return timestamp Long
     */
    fun getTimestampWithDate(day: Int, month: Int, year: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month + 1, day)
        return calendar.timeInMillis
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
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

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
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        return Triple(day, month, year)
    }

    @SuppressLint("NewApi")
    fun getLocalDateWithTimestamp(timestamp: Long): LocalDate {
        return Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.of("America/Sao_Paulo"))
            .toLocalDate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDaysOfWeekTimestamps(weekDayName: String): List<Long> {
        val year = LocalDate.now().year
        val dayOfWeek = DayOfWeek.valueOf(weekDayName.uppercase())
        val startDate = LocalDate.of(year, 1, 1)
        val endDate = LocalDate.of(year, 12, 31)

        val timestamps = mutableListOf<Long>()
        var currentDate = startDate.with(TemporalAdjusters.nextOrSame(dayOfWeek))

        while (!currentDate.isAfter(endDate)) {
            timestamps.add(currentDate.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli())
            currentDate = currentDate.plusWeeks(1)
        }

        return timestamps
    }
}
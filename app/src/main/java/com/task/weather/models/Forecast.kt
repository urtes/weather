package com.task.weather.models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Forecast (val dateTimeString: String, val conditions: String, val temp: String, val icon: String) {
    private val dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    val time: String = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    val dayOfWeek = dateTime.format(DateTimeFormatter.ofPattern("EEEE"))
}
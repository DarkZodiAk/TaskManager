package com.example.taskmanager.records.data.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object LocalDateTimeConverter {
    private const val UTC = "UTC"

    fun longToLocalDateTimeWithTimezone(timestamp: Long): LocalDateTime {
        val utcTime = LocalDateTime
            .ofInstant(
                Instant.ofEpochSecond(timestamp),
                ZoneId.of(UTC)
            )
        return utcTime
            .atZone(ZoneId.of(UTC))
            .withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()))
            .toLocalDateTime()
    }

    fun getEpochForUTC(localDateTime: LocalDateTime) : Long {
        return localDateTime
            .atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneId.of(UTC))
            .toEpochSecond()
    }

    fun getCurrentEpoch(): Long {
        return System.currentTimeMillis()
    }
}
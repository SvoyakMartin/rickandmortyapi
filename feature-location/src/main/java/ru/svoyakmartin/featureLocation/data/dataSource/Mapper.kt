package ru.svoyakmartin.featureLocation.data.dataSource

import ru.svoyakmartin.featureLocation.domain.model.Location
import ru.svoyakmartin.featureLocation.data.model.LocationDTO
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun LocationDTO.toLocation() = Location(
    id,
    name,
    type,
    dimension,
    url,
    getTimeFromString(created)
)

fun getTimeFromString(timeStamp: String): Long {
    return LocalDateTime.parse(timeStamp, DateTimeFormatter.ISO_DATE_TIME)
        .toInstant(ZoneOffset.UTC)
        .toEpochMilli()
}
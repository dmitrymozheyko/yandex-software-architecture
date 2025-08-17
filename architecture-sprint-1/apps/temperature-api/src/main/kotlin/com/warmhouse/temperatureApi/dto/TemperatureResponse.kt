package com.warmhouse.temperatureApi.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

data class TemperatureResponse(
    val value: Double,
    val unit: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    val timestamp: ZonedDateTime,
    val location: String,
    val status: Status,
    @JsonProperty(value = "sensor_id")
    val sensorId: String,
    val sensorType: String,
    val description: String,
) {
    enum class Status {
        active,
        inactive,
    }

    enum class SensorType {
        SENSOR,
        RELAY,
    }
}

package com.warmhouse.temperatureApi.service

import com.warmhouse.temperatureApi.dto.TemperatureResponse
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import kotlin.random.Random

@Service
class TemperatureService {
    fun temperature(sensorId: String?, location: String?): TemperatureResponse {
        val location = Location.determine(
            sensorId = sensorId,
            location = location,
        )
        return TemperatureResponse(
            value = RANDOM.nextDouble() * 10,
            unit = "Degrees Celsius",
            timestamp = ZonedDateTime.now(),
            location = location.location,
            status = if (RANDOM.nextBoolean()) {
                TemperatureResponse.Status.active
            } else {
                TemperatureResponse.Status.inactive
            },
            sensorId = location.sensorId,
            sensorType = "Temperature",
            description = "",
        )
    }
    companion object {
        private val RANDOM = Random
    }
}

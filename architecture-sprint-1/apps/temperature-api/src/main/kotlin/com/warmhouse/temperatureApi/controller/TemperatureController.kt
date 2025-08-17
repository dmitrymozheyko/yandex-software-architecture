package com.warmhouse.temperatureApi.controller

import com.warmhouse.temperatureApi.dto.TemperatureResponse
import com.warmhouse.temperatureApi.service.TemperatureService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("temperature")
@Tag(name = "API Температуры")
class TemperatureController(
    private val temperatureService: TemperatureService,
) {
    @Operation(summary = "Получение температуры с сенсора")
    @GetMapping("{sensorId}")
    fun get(
        @Parameter(description = "идентификатор названия комнаты")
        @PathVariable("sensorId") sensorId: String?,

        @Parameter(description = "название комнаты")
        @RequestParam("location") location: String?,
    ): TemperatureResponse = temperatureService.temperature(sensorId, location)
}

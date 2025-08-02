package com.warmhouse.temperatureApi.service

enum class Location(val sensorId: String, val location: String) {
    LIVING_ROOM("1","Living Room"),
    BEDROOM("2", "Bedroom"),
    KITCHEN("3","Kitchen"),
    UNKNOWN("0", "Unknown");

    companion object {
        fun determine(sensorId: String?, location: String?): Location {
            val resultByLocation = location?.let { l ->
                entries.firstOrNull { it.location == l }
            }
            val resultBySensorId = sensorId?.let { s ->
                entries.firstOrNull { it.sensorId == s }
            }
            return resultByLocation ?: resultBySensorId ?: UNKNOWN
        }
    }
}

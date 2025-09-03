package ru.yandex.practicum.architecturesprint2.events.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class User(
    @JsonProperty("user_id")
    val userId: Long?,
    val username: String?,
    val action: String?,
    val timestamp: LocalDateTime,
    val status: String?,
)

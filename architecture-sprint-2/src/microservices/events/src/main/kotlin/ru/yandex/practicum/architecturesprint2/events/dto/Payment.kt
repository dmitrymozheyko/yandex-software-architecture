package ru.yandex.practicum.architecturesprint2.events.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class Payment(
    @JsonProperty("payment_id")
    val paymentId: Long?,
    @JsonProperty("user_id")
    val userId: Long?,
    val amount: Float?,
    val status: String?,
    val timestamp: LocalDateTime,
)

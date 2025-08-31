package ru.yandex.practicum.architecturesprint2.events.dto

data class Movie(
    val id: Long?,
    val title: String?,
    val description: String?,
    val genres: List<String>?,
    val rating: Float?,
    val status: String?,
)

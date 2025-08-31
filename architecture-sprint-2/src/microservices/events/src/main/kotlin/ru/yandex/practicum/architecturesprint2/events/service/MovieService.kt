package ru.yandex.practicum.architecturesprint2.events.service

import org.springframework.stereotype.Service
import ru.yandex.practicum.architecturesprint2.events.dto.Movie
import ru.yandex.practicum.architecturesprint2.events.kafka.KafkaProducer
import kotlin.random.Random

@Service
class MovieService(
    private val kafkaProducer: KafkaProducer
) {

    private val random = Random(System.currentTimeMillis())

    fun register(movie: Movie): Movie = movie.copy(
        id = random.nextLong(),
        status = "success",
    ).also {
        kafkaProducer.sendMovieEvent(it)
    }
}

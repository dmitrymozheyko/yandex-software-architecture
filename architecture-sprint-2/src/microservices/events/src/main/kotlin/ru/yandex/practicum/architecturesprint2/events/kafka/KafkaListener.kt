package ru.yandex.practicum.architecturesprint2.events.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.yandex.practicum.architecturesprint2.events.dto.Movie
import ru.yandex.practicum.architecturesprint2.events.dto.Payment
import ru.yandex.practicum.architecturesprint2.events.dto.User

@Component
class KafkaListener(
    private val objectMapper: ObjectMapper,
) {
    private val logger: KLogger = KotlinLogging.logger { }

    @KafkaListener(topics = [KafkaTopics.MOVIE_TOPIC], groupId = "movie-group")
    fun processMovie(@Payload data: String) {
        logger.info { "Processing movie: $data" }
        val movie = objectMapper.readValue<Movie>(data)
        logger.info { "Successfully processed movie: $movie" }
    }

    @KafkaListener(topics = [KafkaTopics.USER_TOPIC], groupId = "user-group")
    fun processUser(@Payload data: String) {
        logger.info { "Processing user: $data" }
        val movie = objectMapper.readValue<User>(data)
        logger.info { "Successfully processed user: $movie" }
    }

    @KafkaListener(topics = [KafkaTopics.PAYMENT_TOPIC], groupId = "payment-group")
    fun processPayment(@Payload data: String) {
        logger.info { "Processing payment: $data" }
        val payment = objectMapper.readValue<Payment>(data)
        logger.info { "Successfully processed payment: $payment" }
    }

}

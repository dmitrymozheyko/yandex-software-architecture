package ru.yandex.practicum.architecturesprint2.events.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import ru.yandex.practicum.architecturesprint2.events.dto.Movie
import ru.yandex.practicum.architecturesprint2.events.dto.Payment
import ru.yandex.practicum.architecturesprint2.events.dto.User

@Component
class KafkaProducer(
    private val objectMapper: ObjectMapper,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun sendMovieEvent(movie: Movie) {
        sendEvent(KafkaTopics.MOVIE_TOPIC, objectMapper.writeValueAsString(movie))
    }

    fun sendUserEvent(user: User) {
        sendEvent(KafkaTopics.USER_TOPIC, objectMapper.writeValueAsString(user))
    }

    fun sendPaymentEvent(payment: Payment) {
        sendEvent(KafkaTopics.PAYMENT_TOPIC, objectMapper.writeValueAsString(payment))
    }

    private fun sendEvent(topic: String, event: String) {
        val kafkaMessage = MessageBuilder
            .withPayload(event)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .build()
        kafkaTemplate.send(kafkaMessage)
    }
}

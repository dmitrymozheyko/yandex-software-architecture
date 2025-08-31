package ru.yandex.practicum.architecturesprint2.events.service

import org.springframework.stereotype.Service
import ru.yandex.practicum.architecturesprint2.events.dto.User
import ru.yandex.practicum.architecturesprint2.events.kafka.KafkaProducer

@Service
class UserService(
    private val kafkaProducer: KafkaProducer
) {
    fun register(user: User): User = user.copy(
        status = "success"
    ).also {
        kafkaProducer.sendUserEvent(it)
    }
}

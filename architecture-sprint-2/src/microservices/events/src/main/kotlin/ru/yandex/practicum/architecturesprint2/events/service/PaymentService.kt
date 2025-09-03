package ru.yandex.practicum.architecturesprint2.events.service

import org.springframework.stereotype.Service
import ru.yandex.practicum.architecturesprint2.events.dto.Payment
import ru.yandex.practicum.architecturesprint2.events.kafka.KafkaProducer

@Service
class PaymentService(
    private val kafkaProducer: KafkaProducer
) {
    fun register(payment: Payment): Payment = payment.copy(
        status = "success"
    ).also {
        kafkaProducer.sendPaymentEvent(it)
    }
}

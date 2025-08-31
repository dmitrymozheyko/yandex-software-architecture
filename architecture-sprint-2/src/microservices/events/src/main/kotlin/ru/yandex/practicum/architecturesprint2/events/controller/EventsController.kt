package ru.yandex.practicum.architecturesprint2.events.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.yandex.practicum.architecturesprint2.events.dto.Health
import ru.yandex.practicum.architecturesprint2.events.dto.Movie
import ru.yandex.practicum.architecturesprint2.events.dto.Payment
import ru.yandex.practicum.architecturesprint2.events.dto.User
import ru.yandex.practicum.architecturesprint2.events.service.MovieService
import ru.yandex.practicum.architecturesprint2.events.service.PaymentService
import ru.yandex.practicum.architecturesprint2.events.service.UserService

@RestController
@RequestMapping
class EventsController(
    private val movieService: MovieService,
    private val userService: UserService,
    private val paymentService: PaymentService,
) {
    @GetMapping("/health")
    fun health(): ResponseEntity<Health> = ResponseEntity.ok(Health(true))

    @PostMapping("/movie")
    fun movieEvent(@RequestBody movie: Movie): ResponseEntity<Movie> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(movieService.register(movie))

    @PostMapping("/user")
    fun userEvent(@RequestBody user: User) =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.register(user))

    @PostMapping("/payment")
    fun paymentEvent(@RequestBody payment: Payment) =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(paymentService.register(payment))
}

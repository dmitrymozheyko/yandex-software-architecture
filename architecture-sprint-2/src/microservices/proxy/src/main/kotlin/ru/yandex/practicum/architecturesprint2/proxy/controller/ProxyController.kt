package ru.yandex.practicum.architecturesprint2.proxy.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.yandex.practicum.architecturesprint2.proxy.service.ProxyService


@RestController
@RequestMapping("/")
class ProxyController(
    private val proxyService: ProxyService,
) {
    @GetMapping("/health")
    fun health(): ResponseEntity<Boolean> = ResponseEntity.ok(true)

    @RequestMapping(value = ["/**"])
    fun handleProxyRequest(request: HttpServletRequest): ResponseEntity<*> {
        return proxyService.doProxy(request)
    }}

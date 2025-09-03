package ru.yandex.practicum.architecturesprint2.proxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.yandex.practicum.architecturesprint2.proxy.configuration.ProxyConfiguration

@SpringBootApplication
@EnableConfigurationProperties(ProxyConfiguration::class)
class ProxyApplication

fun main(args: Array<String>) {
	runApplication<ProxyApplication>(*args)
}

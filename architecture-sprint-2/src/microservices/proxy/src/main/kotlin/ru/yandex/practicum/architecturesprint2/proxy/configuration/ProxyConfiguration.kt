package ru.yandex.practicum.architecturesprint2.proxy.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "proxy")
class ProxyConfiguration(
    val monolithUrl: String,
    val moviesServiceUrl: String,
    var eventsServiceUrl: String,
    val gradualMigration: Boolean,
    val moviesMigrationPercent: Int,
)

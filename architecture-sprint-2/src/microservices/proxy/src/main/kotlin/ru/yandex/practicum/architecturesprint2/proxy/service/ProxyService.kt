package ru.yandex.practicum.architecturesprint2.proxy.service

import jakarta.servlet.http.HttpServletRequest
import kong.unirest.core.HttpResponse
import kong.unirest.core.Unirest
import org.apache.commons.io.IOUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.yandex.practicum.architecturesprint2.proxy.configuration.ProxyConfiguration
import kotlin.random.Random

@Service
class ProxyService(
    private val proxyConfiguration: ProxyConfiguration,
) {
    fun doProxy(request: HttpServletRequest): ResponseEntity<*> {
        try {
            if (!ALLOWED_METHODS.contains(request.method.uppercase())) {
                return ResponseEntity.status(405).build<Any?>()
            }
            val result = exchange(
                targetRequestURI = getTargetServer(request.servletPath) + request.servletPath,
                request = request,
                headers = getRequestHeaders(request),
                parameters = request.parameterNames.toList().associateWith { request.getParameter(it) },
            ) ?: return ResponseEntity.status(404).build<Any?>()

            val headersResult = HttpHeaders()
            result.headers.all().map { header ->
                headersResult.add(header.name, header.value)
            }

            val contentType: String = result.headers.getFirst("content-type")

            return if (contentType.contains("image")) {
                ResponseEntity.status(result.status)
                    .headers(headersResult)
                    .body(result.getBody())
            } else {
                val resultString = String(result.body)
                    .replace("https://".toRegex(), "http://")
                ResponseEntity.status(result.status)
                    .headers(headersResult)
                    .body<String?>(resultString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.internalServerError().build<Any?>()
        }
    }

    private fun getTargetServer(servletPath: String): String =
        // Запрос к сервису movies
        if (servletPath.startsWith(MOVIES_PATH)) {
            // Включено частичное внедрение
            if (proxyConfiguration.gradualMigration) {
                if (RANDOM.nextInt(100) < proxyConfiguration.moviesMigrationPercent) {
                    // Сработало переключение на новый сервис
                    proxyConfiguration.moviesServiceUrl
                } else {
                    // Вызываем монолит
                    proxyConfiguration.monolithUrl
                }
            } else {
                // Частичное внедрение отключено, вызываем микросервис
                proxyConfiguration.moviesServiceUrl
            }
        // Запрос к сервису events (частичное внедрение не используется)
        } else if (servletPath.startsWith(EVENTS_PATH)) {
            proxyConfiguration.eventsServiceUrl
        // Всё остальное в монолит
        } else {
            proxyConfiguration.monolithUrl
        }


    private fun getRequestHeaders(request: HttpServletRequest): Map<String, String> {
        val headers = request.headerNames.toList().associateWith { request.getHeader(it) }.toMutableMap()
        headers.remove("host")
        headers.remove("connection")
        headers.remove("content-length")
        headers.remove("upgrade")
        headers["via"] = "1.1 architecture.sprint2.proxy.service"
        return headers.toMap()
    }

    private fun exchange(
        targetRequestURI: String,
        request: HttpServletRequest,
        parameters: Map<String, String>,
        headers: Map<String, String>,
    ): HttpResponse<ByteArray>? =
        when (request.method.uppercase()) {
            "GET" -> Unirest.get(targetRequestURI)
                .queryString(parameters)
                .headers(headers)
                .asBytes()

            "POST" -> Unirest.post(targetRequestURI)
                .queryString(parameters)
                .headers(headers)
                .body(IOUtils.toString(request.reader))
                .asBytes()

            "PUT" -> Unirest.put(targetRequestURI)
                .queryString(parameters)
                .headers(headers)
                .body(IOUtils.toString(request.reader))
                .asBytes()

            "PATCH" -> Unirest.patch(targetRequestURI)
                .queryString(parameters)
                .headers(headers)
                .body(IOUtils.toString(request.reader))
                .asBytes()

            else -> null
        }

    companion object {
        private const val MOVIES_PATH = "/api/movies"
        private const val EVENTS_PATH = "/api/events"
        private val ALLOWED_METHODS = listOf(
            "POST",
            "GET",
            "PATCH",
            "PUT",
        )
        private val RANDOM = Random(System.currentTimeMillis())
    }
}

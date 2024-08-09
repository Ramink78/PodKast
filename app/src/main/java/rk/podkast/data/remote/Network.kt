package rk.podkast.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
import kotlinx.serialization.json.Json

object Network {
    private const val TIMEOUT_DURATION: Long = 60_000
    val client = HttpClient(Android) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "listen-api.listennotes.com"
                path("api/v2/")
            }
            headers.appendIfNameAbsent("X-ListenAPI-Key", "apikey")
        }
        expectSuccess = true
        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT_DURATION
            connectTimeoutMillis = TIMEOUT_DURATION
            socketTimeoutMillis = TIMEOUT_DURATION
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }
}

package rk.podkast.data.remote.util

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> HttpClient.safeRequest(block: HttpRequestBuilder.() -> Unit): Response<T, DataError> {
    return try {
        val response = request { block() }
        Response.Success(data = response.body())
    } catch (e: Exception) {
        e.printStackTrace()
        when (e) {
            is ClientRequestException -> Response.Failure(DataError.NetworkError.CLIENT_ERROR)
            is ServerResponseException -> Response.Failure(DataError.NetworkError.SERVER_ERROR)
            is SerializationException -> Response.Failure(DataError.LocalError.SERIALIZATION_ERROR)
            is HttpRequestTimeoutException -> Response.Failure(DataError.NetworkError.CONNECTION_TIMEOUT)
            is IOException -> Response.Failure(DataError.NetworkError.CONNECTION_ERROR)
            else -> Response.Failure(DataError.GenericError(message = e.message, "Something wrong"))
        }
    }
}
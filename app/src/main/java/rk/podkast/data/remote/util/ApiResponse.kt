package rk.podkast.data.remote.util

sealed interface Error
sealed interface Response<out D, out E> {
    data class Success<out D, out E>(val data: D) : Response<D, E>
    data class Failure<out D, out E>(val error: Error) : Response<D, E>
}

sealed interface DataError : Error {
    enum class NetworkError : DataError {
        CONNECTION_ERROR,
        CONNECTION_TIMEOUT,
        CLIENT_ERROR,
        SERVER_ERROR
    }

    enum class LocalError : DataError {
        SERIALIZATION_ERROR
    }

    data class GenericError(
        val message: String?,
        val errorMessage: String
    ) : DataError
}




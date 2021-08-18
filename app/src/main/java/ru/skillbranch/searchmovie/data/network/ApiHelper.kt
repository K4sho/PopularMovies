package ru.skillbranch.searchmovie.data.network

import java.io.IOException

// Вспомогательная функция. Позволяет вызвать метод запроса в корутине и передать сообщение, которое будет выброшено при ошибке
suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> =
    try {
        call.invoke()
    } catch (e: Exception) {
        Result.Error(IOException(errorMessage, e))
    }

class ApiHelper(private val apiService: ApiService) {

}
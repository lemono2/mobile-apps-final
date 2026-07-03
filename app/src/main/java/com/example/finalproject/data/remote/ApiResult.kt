package com.example.finalproject.data.remote

import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(block: suspend () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

fun Throwable.toUserMessage(): String = when (this) {
    is IOException -> "No internet connection. Check your network and try again."
    is HttpException -> "Server error (${code()}). Please try again later."
    else -> "Something went wrong. Please try again."
}

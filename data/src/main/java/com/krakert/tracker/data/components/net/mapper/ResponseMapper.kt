package com.krakert.tracker.data.components.net.mapper

import com.krakert.tracker.domain.response.AuthException
import com.krakert.tracker.domain.response.BackendException
import retrofit2.Response
import javax.inject.Inject

class ResponseMapper @Inject constructor() {

    fun <T> map(response: Response<T>): Result<T> = when {
        response.isSuccessful -> response.body()?.let(Result.Companion::success) ?: Result.failure(
            exception = Throwable("Http body is empty. HttpMessage: ${response.message()} HttpCode: ${response.code()}")
        )
        response.code() == 401 -> Result.failure(
            exception = AuthException()
        )
        else -> Result.failure(
            BackendException(
                errorCode = response.code()
            )
        )
    }

    fun <T> mapEmptyBody(response: Response<T>): Result<Unit> = when {
        response.isSuccessful -> Result.success(Unit)
        response.code() == 401 -> Result.failure(AuthException())
        else -> Result.failure(BackendException(response.code()))
    }

    fun mapNoContent(response: Response<Unit>): Result<Unit> = when {
        response.isSuccessful -> Result.success(Unit)
        response.code() == 401 -> Result.failure(
            exception = AuthException()
        )
        else -> Result.failure(
            exception = BackendException(
                errorCode = response.code()
            )
        )
    }
}
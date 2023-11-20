package com.krakert.tracker.data.extension

fun <T> T?.requireNotNull(): T = requireNotNull(this)

fun <T> T?.requireNotNull(lazyMessage: () -> Any): T = requireNotNull(this, lazyMessage)

inline fun <R, T : R> Result<T>.guard(onFailure: (failure: Result<Nothing>) -> R): R {
    return getOrElse { onFailure(Result.failure(it)) }
}
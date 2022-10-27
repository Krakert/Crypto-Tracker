package com.stakt.tracker.api

import com.stakt.tracker.models.ui.ProblemState

sealed class Resource<T>(
    val data: T? = null,
    val state: ProblemState? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(state: ProblemState?, data: T? = null) : Resource<T>(data, state)
    class Loading<T> : Resource<T>()
}

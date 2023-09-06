package com.krakert.tracker.data.tracker.api

import com.krakert.tracker.data.tracker.entity.ProblemStateEntity


sealed class Resource<T>(
    val data: T? = null,
    val state: ProblemStateEntity? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(state: ProblemStateEntity?, data: T? = null) : Resource<T>(data, state)
    class Loading<T> : Resource<T>()
}
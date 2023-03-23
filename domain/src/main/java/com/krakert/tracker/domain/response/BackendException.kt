package com.krakert.tracker.domain.response

import java.lang.Exception

data class BackendException(
    val errorCode: Int
) : Exception()

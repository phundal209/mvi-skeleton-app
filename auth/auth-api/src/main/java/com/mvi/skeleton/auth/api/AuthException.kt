package com.mvi.skeleton.auth.api

import java.lang.Exception

class AuthException(val input: String, val type: AuthExceptionType) : Exception() {
    override val message: String?
        get() = input

    val exceptionType: AuthExceptionType
        get() = type
}

enum class AuthExceptionType() {
    CANCELLED,
    COMPLETED,
    FAILED
}
package com.mvi.skeleton.auth.api

interface AuthCallback<T> {
    fun onSuccess(result: T)
    fun onFailure(exception: AuthException)
}
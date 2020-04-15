package com.mvi.skeleton.auth

sealed class LoginViewEvent {
    data class SignUpWithCreds(val email: String, val password: String) : LoginViewEvent()
    data class LoginClicked(val email: String, val password: String) : LoginViewEvent()
}
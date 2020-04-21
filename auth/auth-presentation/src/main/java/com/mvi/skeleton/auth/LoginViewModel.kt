package com.mvi.skeleton.auth

import androidx.lifecycle.viewModelScope
import com.mvi.skeleton.auth.api.*
import com.mvi.skeleton.template.BaseViewModel
import com.mvi.skeleton.template.Status
import com.mvi.skeleton.template.ViewEffect
import com.mvi.skeleton.template.ViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

open class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<ViewState<User>, ViewEffect, LoginViewEvent>() {

    init {
        viewState = ViewState(status = Status.NotFetched, datas = emptyList())
        initAuth()
    }

    private fun initAuth() {
        viewState = viewState.copy(status = Status.Fetching)
        viewModelScope.launch {
            viewState = if (authRepository.isLoggedIn()) {
                viewState.copy(status = Status.Fetched, data = authRepository.getUser())
            } else {
                viewState.copy(status = Status.NotFetched)
            }
        }
    }

    override fun process(viewEvent: LoginViewEvent) {
        super.process(viewEvent)
        when (viewEvent) {
            is LoginViewEvent.LoginClicked -> {
                handleLoginClicked(viewEvent.email, viewEvent.password, AuthType.LOGIN)
            }
            is LoginViewEvent.SignUpWithCreds -> {
                handleLoginClicked(viewEvent.email, viewEvent.password, AuthType.CREATE_ACCOUNT)
            }
        }
    }

    private fun handleLoginClicked(email: String, password: String, authType: AuthType) {
        viewState = viewState.copy(status = Status.Fetching)
        viewModelScope.launch {
            authRepository.loginOrCreate(email, password, authType, authCallback)
        }
    }

    private val authCallback: AuthCallback<User> = object : AuthCallback<User> {
        override fun onSuccess(result: User) {
            viewState = viewState.copy(status = Status.Fetched, data = result)
            viewEffect = ViewEffect.ShowSnackbar("Logged in with ${result.email}")
        }

        override fun onFailure(exception: AuthException) {
            viewState = viewState.copy(status = Status.ErrorFetching(exception.message?: "Error, please try again"))
            viewEffect = ViewEffect.ShowSnackbar(exception.message?: "Error, please try again")
        }

    }
}
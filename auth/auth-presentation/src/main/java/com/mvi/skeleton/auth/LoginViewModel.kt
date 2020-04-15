package com.mvi.skeleton.auth

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.mvi.skeleton.auth.api.AuthRepository
import com.mvi.skeleton.auth.api.AuthType
import com.mvi.skeleton.template.BaseViewModel
import com.mvi.skeleton.template.Status
import com.mvi.skeleton.template.ViewEffect
import com.mvi.skeleton.template.ViewState
import com.mvi.skeleton.user.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    application: Application,
    private val authRepository: AuthRepository) :
    BaseViewModel<ViewState<User>, ViewEffect, LoginViewEvent>(application) {

    init {
        viewState = ViewState(status = Status.NotFetched, datas = emptyList())
        initAuth()
    }

    private fun initAuth() {
        viewState = viewState.copy(status = Status.Fetching)
        viewModelScope.launch {
            if (authRepository.isLoggedIn()) {
                viewState = viewState.copy(status = Status.Fetched, data = authRepository.getUser())
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
            val result = authRepository.loginOrCreate(email, password, authType)
            result.onSuccess {
                viewState = viewState.copy(status = Status.Fetched, data = it)
                viewEffect = ViewEffect.ShowToast("Already logged in with $email")
            }
            result.onFailure {
                viewState = viewState.copy(status = Status.ErrorFetching(it.message?: "Error, please try again"))
                viewEffect = ViewEffect.ShowSnackbar(it.message?: "Error, please try again")
            }
        }
    }
}
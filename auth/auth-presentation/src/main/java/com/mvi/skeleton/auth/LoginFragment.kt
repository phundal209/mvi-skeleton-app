package com.mvi.skeleton.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mvi.skeleton.auth.databinding.AuthFragmentBinding
import com.mvi.skeleton.template.BaseFragment
import com.mvi.skeleton.template.Status
import com.mvi.skeleton.template.ViewEffect
import com.mvi.skeleton.template.ViewState
import com.mvi.skeleton.user.User

class LoginFragment: BaseFragment<ViewState<User>, ViewEffect, LoginViewEvent,
        LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    private var _binding: AuthFragmentBinding? = null
    private val binding get() = _binding!!
    private var loginPageState: LoginPageState = LoginPageState.LoginOrSignUp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AuthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleClicks()
    }

    private fun handleClicks() {
        binding.submitButton.setOnClickListener {
            if (loginPageState == LoginPageState.LoginOrSignUp) {
                viewModel.process(
                    LoginViewEvent.LoginClicked(
                        binding.emailTextView.text.toString(),
                        binding.passwordTextView.text.toString()
                    )
                )
            } else if (loginPageState == LoginPageState.CreateOrLogin) {
                viewModel.process(
                    LoginViewEvent.SignUpWithCreds(
                        binding.emailTextView.text.toString(),
                        binding.passwordTextView.text.toString()
                    )
                )
            }
        }
        binding.signUpButton.setOnClickListener {
            if (loginPageState == LoginPageState.LoginOrSignUp) {
                binding.submitButton.text = context?.getString(R.string.create_account)
                binding.signUpButton.text = context?.getString(R.string.have_account_login)
                loginPageState = LoginPageState.CreateOrLogin
            } else if (loginPageState == LoginPageState.CreateOrLogin) {
                binding.submitButton.text = context?.getString(R.string.login)
                binding.signUpButton.text = context?.getString(R.string.signup)
                loginPageState = LoginPageState.LoginOrSignUp
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderViewState(viewState: ViewState<User>) {
        when (viewState.status) {
            is Status.NotFetched -> { }
            is Status.Fetching -> {
                binding.progressLoader.visibility = View.VISIBLE
            }
            is Status.ErrorFetching -> {
                binding.progressLoader.visibility = View.GONE
            }
            is Status.Fetched -> {
                binding.progressLoader.visibility = View.GONE
                // transition user to next page
            }
        }
    }

    override fun renderViewEffect(viewEffect: ViewEffect) {
        when (viewEffect) {
            is ViewEffect.ShowSnackbar -> {
                showSnackBar(binding.myCoordinatorLayout, viewEffect.message)
            }
            is ViewEffect.ShowToast -> {
                showToast(context, viewEffect.message)
            }
        }
    }
}

enum class LoginPageState {
    LoginOrSignUp, // Base state, shows either a button to login or sign up
    CreateOrLogin // If clicked sign up, either create or login with existing account
}
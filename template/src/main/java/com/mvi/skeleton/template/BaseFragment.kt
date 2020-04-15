package com.mvi.skeleton.template

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<State, Effect, Event, ViewModel : BaseViewModel<State, Effect, Event>>
    : Fragment() {

    abstract val viewModel : ViewModel

    private val viewStateObserver = Observer<State> {
        Log.d("Fragment", "observer viewState : $it")
        renderViewState(it)
    }

    private val viewEffectObserver = Observer<Effect> {
        Log.d("Fragment", "observer viewEffect : $it")
        renderViewEffect(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewStates().observe(viewLifecycleOwner, viewStateObserver)
        viewModel.viewEffects().observe(viewLifecycleOwner, viewEffectObserver)
    }

    abstract fun renderViewState(viewState: State)

    abstract fun renderViewEffect(viewEffect: Effect)

    fun showSnackBar(view: View, resId: Int, length: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(
            view,
            resId,
            length
        ).show()
    }

    fun showSnackBar(view: View, charInput: CharSequence, length: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(
            view,
            charInput,
            length
        ).show()
    }

    fun showToast(context: Context?, message: String, length: Int = Toast.LENGTH_LONG) {
        context?.let {
            Toast.makeText(context, message, length).show()
        }
    }


}
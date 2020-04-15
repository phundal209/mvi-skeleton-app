package com.mvi.skeleton.template

import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<ViewState, ViewEffect, ViewEvent> :
        ViewModel(), ViewModelContract<ViewEvent> {

    private val _viewStates: MutableLiveData<ViewState> = MutableLiveData()
    fun viewStates(): LiveData<ViewState> = _viewStates

    private var _viewState: ViewState? = null
    var viewState: ViewState
        get() = _viewState ?:
            throw UninitializedPropertyAccessException("\"viewState\" was queried before being init")
        set(value) {
            Log.d("ViewModel", "settingViewState : $value")
            _viewState = value
            _viewStates.value = value
        }

    private val _viewEffects: SingleLiveEvent<ViewEffect> = SingleLiveEvent()
    fun viewEffects(): SingleLiveEvent<ViewEffect> = _viewEffects

    private var _viewEffect: ViewEffect? = null
    var viewEffect: ViewEffect
        get() = _viewEffect ?:
            throw UninitializedPropertyAccessException("\"viewEffect\" was queried before being init")
        set(value) {
            Log.d("ViewModel", "settingViewEffect : $value")
            _viewEffect = value
            _viewEffects.value = value
        }


    @CallSuper
    override fun process(viewEvent: ViewEvent) {
        Log.d("ViewModel", "Processing viewEvent: $viewEvent")
    }
}
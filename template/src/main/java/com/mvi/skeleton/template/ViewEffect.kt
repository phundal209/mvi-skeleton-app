package com.mvi.skeleton.template

/**
 * A ViewEffect is a fire and forget mechanism that does not maintain
 * state. For example, showing a toast would not be something a state
 * is maintained for. Therefore, it is recommended to use [SingleLiveEvent]
 * when displaying an effect.
 */

sealed class ViewEffect {
    data class ShowSnackbar(val message: String) : ViewEffect()
    data class ShowToast(val message: String) : ViewEffect()
}
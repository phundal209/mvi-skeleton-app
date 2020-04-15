package com.mvi.skeleton.template

/**
 * View observes this domain layer for model state changes.
 * [ViewState] should represent the current state of the view at any time
 * Every time there is any user input or action you *must* copy the previous
 * state and transform as necessary so that we preserve previous state.
 */
data class ViewState<T>(
    val status: Status,
    val datas: List<T>,
    val data: T? = null
)

sealed class Status {
    object NotFetched : Status()
    object Fetching : Status()
    object Fetched: Status()
    data class ErrorFetching(val message: String) : Status()
}
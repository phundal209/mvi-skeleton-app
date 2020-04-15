package com.mvi.skeleton.template

interface ViewModelContract<T> {

    fun process(viewEvent: T)
}
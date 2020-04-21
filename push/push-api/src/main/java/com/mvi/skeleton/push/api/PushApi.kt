package com.mvi.skeleton.push.api

interface PushApi {
    fun register(onPush: OnPush)
    fun unregister(push: OnPush)
}

interface OnPush {
    fun onPush(push: PushMessage)
}
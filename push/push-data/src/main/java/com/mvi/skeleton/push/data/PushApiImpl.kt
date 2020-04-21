package com.mvi.skeleton.push.data

import com.google.firebase.messaging.FirebaseMessagingService

class PushApiImpl(
) : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    fun getCurrentToken() {

    }
}
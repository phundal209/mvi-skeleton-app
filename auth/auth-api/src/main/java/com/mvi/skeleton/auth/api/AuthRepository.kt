package com.mvi.skeleton.auth.api

import com.mvi.skeleton.user.User

interface AuthRepository {
    suspend fun isLoggedIn(): Boolean
    suspend fun loginOrCreate(email: String, password: String,
                              authType: AuthType, authCallback: AuthCallback<User>)
    suspend fun logout(): Boolean
    suspend fun deleteUser(authCallback: AuthCallback<Boolean>)
    suspend fun getUser(): User
}
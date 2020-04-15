package com.mvi.skeleton.auth.api

import com.mvi.skeleton.user.User

interface AuthRepository {
    suspend fun isLoggedIn(): Boolean
    suspend fun loginOrCreate(email: String, password: String, authType: AuthType): Result<User>
    suspend fun logout(): Boolean
    suspend fun deleteUser(): Result<Boolean>
    suspend fun getUser(): User
}
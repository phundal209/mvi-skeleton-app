package com.mvi.skeleton.auth.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.mvi.skeleton.auth.api.*
import com.mvi.skeleton.user.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun loginOrCreate(email: String, password: String,
                                       authType: AuthType, authCallback: AuthCallback<User>) {
        return when (authType) {
            AuthType.CREATE_ACCOUNT -> {
                val task = auth.signInWithEmailAndPassword(email, password)
                handleAuthResult(task, email, authCallback)
            }
            AuthType.LOGIN -> {
                val task = auth.createUserWithEmailAndPassword(email, password)
                handleAuthResult(task, email, authCallback)
            }
        }
    }

    override suspend fun logout(): Boolean {
        auth.signOut()
        return true
    }

    override suspend fun deleteUser(authCallback: AuthCallback<Boolean>) {
        val deleteTask = auth.currentUser?.delete()
        deleteTask?.addOnSuccessListener {
            authCallback.onSuccess(true)
        }
        deleteTask?.addOnFailureListener {
            authCallback.onFailure(AuthException(it.message ?: "failure", AuthExceptionType.FAILED))
        }
        deleteTask?.addOnCanceledListener {
            authCallback.onFailure(AuthException("cancelled deletion", AuthExceptionType.CANCELLED))
        }
    }

    override suspend fun getUser(): User =
        User(auth.currentUser?.uid?: "", auth.currentUser?.email?: "")

    private fun handleAuthResult(authResult: Task<AuthResult>, email: String, authCallback: AuthCallback<User>) {
        authResult.addOnSuccessListener {
            val user = User(it.user?.uid ?: "", email)
            authCallback.onSuccess(user)
        }
        authResult.addOnCanceledListener {
            authCallback.onFailure(AuthException("cancelled", AuthExceptionType.CANCELLED))
        }
        authResult.addOnFailureListener {
            authCallback.onFailure(AuthException(it.message ?: "failure", AuthExceptionType.FAILED))
        }
    }
}
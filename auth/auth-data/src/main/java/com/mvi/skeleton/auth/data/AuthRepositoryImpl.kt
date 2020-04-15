package com.mvi.skeleton.auth.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.mvi.skeleton.auth.api.AuthRepository
import com.mvi.skeleton.auth.api.AuthException
import com.mvi.skeleton.auth.api.AuthExceptionType
import com.mvi.skeleton.auth.api.AuthType
import com.mvi.skeleton.user.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun loginOrCreate(email: String, password: String, authType: AuthType): Result<User> {
        return when (authType) {
            AuthType.CREATE_ACCOUNT -> {
                val task = auth.signInWithEmailAndPassword(email, password)
                handleAuthResult(task, email)
            }
            AuthType.LOGIN -> {
                val task = auth.createUserWithEmailAndPassword(email, password)
                handleAuthResult(task, email)
            }
        }
    }

    override suspend fun logout(): Boolean {
        auth.signOut()
        return true
    }

    override suspend fun deleteUser(): Result<Boolean> {
        val deleteTask = auth.currentUser?.delete()
        var result: Result<Boolean> = Result.failure(UninitializedPropertyAccessException("user is null"))
        deleteTask?.addOnSuccessListener {
            result = Result.success(true)
        }
        deleteTask?.addOnFailureListener {
            result = Result.failure(AuthException(it.message ?: "failure", AuthExceptionType.FAILED))
        }
        deleteTask?.addOnCanceledListener {
            result = Result.failure(AuthException("cancelled deletion", AuthExceptionType.CANCELLED))
        }
        deleteTask?.addOnCompleteListener {
            result = Result.failure(AuthException("completed but not deleted", AuthExceptionType.COMPLETED))
        }
        return result
    }

    override suspend fun getUser(): User =
        User(auth.currentUser?.uid?: "", auth.currentUser?.email?: "")

    private fun handleAuthResult(authResult: Task<AuthResult>, email: String): Result<User> {
        var result: Result<User> = Result.failure(UninitializedPropertyAccessException("auth not init"))
        authResult.addOnSuccessListener {
            val user = User(it.user?.uid ?: "", email)
            result = Result.success(user)
        }
        authResult.addOnCanceledListener {
            result = Result.failure(AuthException("cancelled", AuthExceptionType.CANCELLED))
        }
        authResult.addOnFailureListener {
            result = Result.failure(AuthException(it.message ?: "failure", AuthExceptionType.FAILED))
        }
        authResult.addOnCompleteListener {
            result = Result.failure(AuthException("completed, but not successful", AuthExceptionType.COMPLETED))
        }
        return result
    }
}
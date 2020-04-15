package com.mvi.skeleton.profile.api

interface ProfileRepository {

    suspend fun getProfile(): Result<Profile>

    suspend fun updateProfile(update: Profile): Result<Profile>

    suspend fun saveProfile(profile: Profile): Result<Profile>

    suspend fun deleteProfile(): Boolean
}
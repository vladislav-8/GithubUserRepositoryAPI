package com.practicum.githubuserrepositoryapi.data

import com.practicum.githubuserrepositoryapi.domain.post_models.AuthRequest
import com.practicum.githubuserrepositoryapi.domain.post_models.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): User
}
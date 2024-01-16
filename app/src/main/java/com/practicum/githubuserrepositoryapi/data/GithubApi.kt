package com.practicum.githubuserrepositoryapi.data

import com.practicum.githubuserrepositoryapi.domain.GithubApiModelItem
import com.practicum.githubuserrepositoryapi.domain.Owner
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("/users/{username}/repos")
    fun getVacancies(
        @Path("username") username: String
    ): Call<List<GithubApiModelItem>>
}
package com.practicum.githubuserrepositoryapi.data

import com.practicum.githubuserrepositoryapi.domain.GithubApiModelItem
import com.practicum.githubuserrepositoryapi.domain.GithubApiRepos
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("/users/{username}/repos")
    suspend fun getVacancies(
        @Path("username") username: String
    ): List<GithubApiModelItem>
}
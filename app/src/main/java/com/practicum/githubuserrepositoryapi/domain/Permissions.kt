package com.practicum.githubuserrepositoryapi.domain

data class Permissions(
    val admin: Boolean,
    val pull: Boolean,
    val push: Boolean
)
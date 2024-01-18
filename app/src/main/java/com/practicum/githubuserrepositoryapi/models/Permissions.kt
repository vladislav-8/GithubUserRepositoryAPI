package com.practicum.searchcompose.models

data class Permissions(
    val admin: Boolean,
    val pull: Boolean,
    val push: Boolean
)
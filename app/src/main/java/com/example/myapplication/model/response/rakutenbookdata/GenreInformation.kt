package com.example.myapplication.model.response.rakutenbookdata

import kotlinx.serialization.Serializable

@Serializable
data class GenreInformation(
    val current: Boolean,
    val id: String,
    val name: String
)

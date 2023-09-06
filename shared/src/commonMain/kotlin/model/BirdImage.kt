package model

import kotlinx.serialization.Serializable


@Serializable
data class BirdImage(
    val description: String? = null,
    val id: Int? = null,
    val url: String? = null,
    val title: String? = null,
    val user: Int? = null
)
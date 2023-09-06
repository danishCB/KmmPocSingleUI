package model

import kotlinx.serialization.Serializable


@Serializable
data class BirdResponse(
    val totalPhotos: Int? = null,
    val message: String? = null,
    val offset: Int? = null,
    val limit: Int? = null,
    val photos: List<BirdImage> = arrayListOf()
)
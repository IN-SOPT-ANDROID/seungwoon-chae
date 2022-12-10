package org.sopt.sample.remote

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMusicDTO(
    val data: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
) {
    @Serializable
    data class Data(
        val id: Int,
        val image: String,
        val singer: String,
        val title: String
    )
}
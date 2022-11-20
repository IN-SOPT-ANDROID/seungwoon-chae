package org.sopt.sample.home.data

import kotlinx.serialization.SerialName

data class Repo(
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val first_name: String,
    @SerialName("avatar") // 이미지
    val image: String
)
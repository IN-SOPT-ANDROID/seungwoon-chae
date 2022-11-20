package org.sopt.sample.home.data

import androidx.annotation.DrawableRes
import kotlinx.serialization.SerialName
import org.sopt.sample.R

data class Repo(
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val first_name: String,
    @SerialName("avatar") // 이미지
    val image: String
)
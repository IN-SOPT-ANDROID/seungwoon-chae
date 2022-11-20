package org.sopt.sample.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseLoginDTO(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: User
) {
    @Serializable
    data class User(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("profileImage")
        val profileImage: String?,
        @SerialName("bio")
        val bio: String?,
        @SerialName("email")
        val email: String,
        // 비밀번호는 평문으로 무방비하게 내려오면 안 된다. 암호화 필요
        @SerialName("password")
        val password: String
    )
}
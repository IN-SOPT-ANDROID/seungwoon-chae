package org.sopt.sample.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePersonDTO(
    @SerialName("page")
    val page: Int,
    @SerialName("per_page")
    val per_page : Int, // 무조건 해야 함
    @SerialName("total")
    val total : Int, // 무조건 해야 함
    @SerialName("total_pages") // 서버에서 내려오는 변수명을 그대로 사용할 경우 시리얼을 붙이지 않아도 된다.
    val total_pages: Int,
    @SerialName("data") // 시리얼 -> 서버통신의 데이터 이름
    val data: List<Person>,
    @SerialName("support")
    val support: Support
)

@Serializable
data class Support(
    @SerialName("url")
    val url: String,
    @SerialName("text")
    val text: String
)

@Serializable
data class Person(
    @SerialName("id")
    val id: Int,
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val first_name: String,
    @SerialName("last_name")
    val last_name: String,
    @SerialName("avatar") // 이미지
    val avatar: String
)


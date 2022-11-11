package org.sopt.sample.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSignUpDTO (
    // JSON 객체로 변환될 때 'email': 'nunu.lee@gmail.com' 과 같은 식으로 들어갈 수 있다는 표기 // string 객체가 안에 들어감. 이것은 JSON에서 응답 객체로 넣어줄 key의 이름, (email)
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String, // 서버에서 실제로 사용하는 Key값과 우리가 사용하는 변수명이 다른 상황이 더 보편적이기 때문에 필요한 기능이다.
    @SerialName("name")
    val name: String,
)
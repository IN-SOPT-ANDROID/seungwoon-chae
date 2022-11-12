package org.sopt.sample.remote

import kotlinx.serialization.SerialName
import org.sopt.sample.home.data.Repo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PersonService {
    @GET("api/users?page=2")
    fun getData(): Call<ResponsePersonDTO>
}

// 서버에서 내려오는 변수명을 그대로 사용할 경우 시리얼을 붙이지 않아도 된다.
package org.sopt.sample.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/user/signin")
    fun login(
        @Body request : RequestLoginDTO
    ): Call<ResponseLoginDTO>
}
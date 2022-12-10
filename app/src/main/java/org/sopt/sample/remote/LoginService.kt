package org.sopt.sample.remote

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface LoginService {
    @POST("api/user/signin")
    fun login(
        @Body request : RequestLoginDTO
    ): Call<ResponseLoginDTO>

    @Multipart
    @POST("api/user/{userId}/image")
    fun uploadProfileImage(
        @Path("userId") userId: Int,
        @Part("image") image: MultipartBody.Part
    ): Call<Unit>
}
package org.sopt.sample.remote

import kotlinx.serialization.Serializable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface MusicAddService {
    @Multipart
    @POST("music")
    fun postMusic(
        @Part image: MultipartBody.Part?,
        @PartMap request: HashMap<String, RequestBody>
    ): Call<ResponseMusicAddDTO>
}
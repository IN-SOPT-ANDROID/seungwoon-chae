package org.sopt.sample.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object musicAPIFactory {
    private val client by lazy {
        OkHttpClient.Builder()
            // .addInterceptor(AuthInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://3.34.53.11:8080/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java) // postman, 서버통신을 해주는 친구라고 생각하자.
    // :: 연산은 고성능을 요구하는 리플렉션 연산, 저것을 통해 모든 클래스에 있는 모든 정보를 조회할 수 있는 강력한 기능.
    // 최소화시키는것이 바람직하다.
}

object MusicServicePool { // 최상위 객체, 일단 생성하면 어디서든 접근할 수 있기에 단 한 번의 생성으로 모든 것을 처리할 수 있음.
    val musicService = musicAPIFactory.create<MusicService>()
    // val musicAddService = APIFactory.create<musicAddService>()
}
package org.sopt.sample.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object PersonAPIFactory {
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
            .baseUrl("https://reqres.in/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }
    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
}

object PersonServicePool { // 최상위 객체, 일단 생성하면 어디서든 접근할 수 있기에 단 한 번의 생성으로 모든 것을 처리할 수 있음.
    val personService = PersonAPIFactory.create<PersonService>()
}
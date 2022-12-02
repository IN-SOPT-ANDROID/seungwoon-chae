package org.sopt.sample.remote

import android.os.Parcel
import android.os.Parcelable
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor, Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        // chain: OkHttp Core 모듈에서 넘어온 chain 객체를 통해서
        // 요청 전 ~ 응답 후의 값을 받아올 수 있음
        // chain.request는 사용자가 보낸 요청값을 가로채는 것
        val originalRequest = chain.request()
        // 서버에서 발급 받은 토큰을 응답값에 저장하는 로직이 있어야 함
        // 그래야 SharedPreference에서 토큰 값을 가져와서 여기에다 넣어줘야지
        // newBuilder는 기존 request 기반으로 새로운 parameter를 추가해서 새로운 request를 만들어내는 것이다
        val headerRequest = originalRequest.newBuilder()
            .header(TOKEN, SERVER_TOKEN)
            .build()
        // proceed를 통해서 서버통신 응답값을 받아올 수 있고 우리는 응답값에 어떠한 커스텀도 안하기에
        // 일단 내려보낸다
        return chain.proceed(headerRequest)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthInterceptor> {
        override fun createFromParcel(parcel: Parcel): AuthInterceptor {
            return AuthInterceptor(parcel)
        }

        override fun newArray(size: Int): Array<AuthInterceptor?> {
            return arrayOfNulls(size)
        }
        private const val SERVER_TOKEN = "서버에서 주는 토큰값 나중에 추가"
        private const val TOKEN = "token"
    }
}
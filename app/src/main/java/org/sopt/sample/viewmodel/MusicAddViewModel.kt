package org.sopt.sample.viewmodel

import ContentUriRequestBody
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.RequestBody
import org.json.JSONObject
import org.sopt.sample.remote.MusicServicePool
import org.sopt.sample.remote.ResponseMusicAddDTO
import org.sopt.sample.remote.ResponseMusicDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicAddViewModel : ViewModel() {
    private val musicAddService = MusicServicePool.musicAddService
    private val _image = MutableLiveData<ContentUriRequestBody>()
    val image: LiveData<ContentUriRequestBody>
        get() = _image

    fun setRequestBody(requestBody: ContentUriRequestBody) {
        _image.value = requestBody
    }

    fun postMusic(map: HashMap<String, RequestBody>) {
        musicAddService.postMusic(
            image.value!!.toFormData(),
            map
        ).enqueue(object : Callback<ResponseMusicAddDTO> {
            override fun onResponse(
                call: Call<ResponseMusicAddDTO>,
                response: Response<ResponseMusicAddDTO>
            ) {
                when (response.code()) {
                    400 -> {
                        Log.e("map: ", map["singer"].toString())
                        Log.e("map: ", map["title"].toString())
                        Log.e("400", "error")
                    }
                    500 -> {
                        Log.e("500", "error")
                    }
                }
                if (response.isSuccessful) {
                    Log.d("tag", "음악 전송 성공")
                }
            }

            override fun onFailure(call: Call<ResponseMusicAddDTO>, t: Throwable) {
                Log.e("server fail", "${t.message.toString()}")
            }
        })
    }
}
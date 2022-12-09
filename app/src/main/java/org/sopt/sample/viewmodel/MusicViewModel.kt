package org.sopt.sample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.RequestBody
import org.sopt.sample.remote.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicViewModel : ViewModel() {
    private val musicService = MusicServicePool.musicService

    private val _getResult: MutableLiveData<ResponseMusicDTO> = MutableLiveData()
    val getResult: LiveData<ResponseMusicDTO>
        get() = _getResult
    private val _successGet = MutableLiveData<Boolean>()
    val successGet: LiveData<Boolean> = _successGet
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun getData() {
        musicService.getData().enqueue(object : Callback<ResponseMusicDTO> {
            override fun onResponse(
                call: Call<ResponseMusicDTO>,
                response: Response<ResponseMusicDTO>
            ) {
                when (response.code()) {
                    400 -> {
                        Log.e("400", "error")
                        _successGet.value = false
                    }
                    500 -> {
                        Log.e("500", "error")
                        _successGet.value = false
                    }
                }
                if (response.isSuccessful) {
                    _getResult.value = response.body()
                    _successGet.value = true
                }
            }

            override fun onFailure(call: Call<ResponseMusicDTO>, t: Throwable) {
                Log.e("server fail", "${t.message.toString()}")
                _successGet.value = false
                _errorMessage.value = t.message
            }
        })
    }


}
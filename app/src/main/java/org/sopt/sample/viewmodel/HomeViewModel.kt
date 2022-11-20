package org.sopt.sample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.home.adapter.PersonAdapter
import org.sopt.sample.remote.Person
import org.sopt.sample.remote.PersonServicePool
import org.sopt.sample.remote.ResponseLoginDTO
import org.sopt.sample.remote.ResponsePersonDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val personService = PersonServicePool.personService

    private val _getResult: MutableLiveData<ResponsePersonDTO> = MutableLiveData()
    val getResult: LiveData<ResponsePersonDTO>
        get() = _getResult
    private val _successGet = MutableLiveData<Boolean>()
    val successGet: LiveData<Boolean> = _successGet
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getData() {
        personService.getData().enqueue(object: Callback<ResponsePersonDTO> {
            override fun onResponse(
                call: Call<ResponsePersonDTO>,
                response: Response<ResponsePersonDTO>
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
                    Log.d("success", "success")
                    _getResult.value = response.body()
                    Log.d("output", response.body().toString())
                    _successGet.value = true
                    //
                }
            }

            override fun onFailure(call: Call<ResponsePersonDTO>, t: Throwable) {
                Log.e("server fail", "${t.message.toString()}")
                _successGet.value = false
                _errorMessage.value = t.message
            }
        })
    }
}

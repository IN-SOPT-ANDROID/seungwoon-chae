package org.sopt.sample.viewmodel

import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.LoginActivity
import org.sopt.sample.remote.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel: ViewModel() {
    private val _signupResult: MutableLiveData<ResponseSignUpDTO> = MutableLiveData()
    val signupResult: LiveData<ResponseSignUpDTO> = _signupResult
    private val signupService = ServicePool.singupService
    private val _successSignup = MutableLiveData<Boolean>()
    val successSignup: LiveData<Boolean> = _successSignup
    private val _serverError = MutableLiveData<String>()
    val serverError: LiveData<String> = _serverError

    val inputEmail = MutableLiveData<String>().apply { value = "" }
    val inputPw = MutableLiveData<String>().apply { value = "" }
    val inputId = MutableLiveData<String>().apply { value = "" }

    fun signup(email: String, password: String, id: String){
        signupService.signup(
            RequestSignUpDTO(email, password, id)
        ).enqueue(object: Callback<ResponseSignUpDTO> {
            override fun onResponse(
                call: Call<ResponseSignUpDTO>,
                response: Response<ResponseSignUpDTO>
            ) {
                if (response.isSuccessful) {
                    _signupResult.value = response.body()
                    _successSignup.value = true
                } else { // 400, 500 등의 오류
                    _serverError.value = response.message()
                    _successSignup.value = false
                }
            }
            override fun onFailure(call: Call<ResponseSignUpDTO>, t: Throwable) { // 서버통신 자체가 실패
                _serverError.value = t.message
                _successSignup.value = false
            }
        }
        )
    }


}
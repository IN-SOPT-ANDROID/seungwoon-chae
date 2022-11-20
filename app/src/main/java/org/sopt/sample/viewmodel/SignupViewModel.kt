package org.sopt.sample.viewmodel

import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BaseObservable
import androidx.lifecycle.*
import org.sopt.sample.LoginActivity
import org.sopt.sample.remote.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignupViewModel: ViewModel() {
    val Emailpattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,10}$"
    val Pwpattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{6,12}$"
    // 형식 검증을 위한 정규표현식

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

    val inputEmailcheck : LiveData<Boolean> = Transformations.map(inputEmail) { email ->
        validEmailcheck(email)
    } // map을 활용해 실시간으로 변하는 input을 검증하여 Boolean 변수를 반환

    val inputPwcheck : LiveData<Boolean> = Transformations.map(inputPw) { pw ->
        validPwcheck(pw)
    }

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
        })
    }

    private fun validEmailcheck(Email: String): Boolean { // 이메일 형식 검증
        val pattern = Pattern.compile(Emailpattern)
        return pattern.matcher(Email).find()
    }

    private fun validPwcheck(Pw: String): Boolean { // 비밀번호 형식 검증
        val pattern = Pattern.compile(Pwpattern)
        return pattern.matcher(Pw).find()
    }
}
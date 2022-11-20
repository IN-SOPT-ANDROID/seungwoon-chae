package org.sopt.sample.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.remote.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel: ViewModel() {
    // backing property: 왜 이런식으로 변수를 만드나? 외부 뷰에서 수정할 필요가 없는 변수
    private val _signupResult: MutableLiveData<ResponseSignUpDTO> = MutableLiveData()
    // 뷰모델에서만 내부 값을 변경 가능하도록 private설정
    val signupResult: LiveData<ResponseSignUpDTO> = _signupResult
    // 이 변수에 접근해서 값을 변경할 수는 없음.
    // 서버통신 값이 오면 loginResult를 선언한다.
    private val signupService = ServicePool.singupService

    private val _successSignup = MutableLiveData<Boolean>()
    val successSignup: LiveData<Boolean> = _successSignup

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage




    fun signup(email: String, password: String, name: String){
        signupService.signup(
            RequestSignUpDTO(email, password, name)
        ).enqueue(object: Callback<ResponseSignUpDTO> {
            override fun onResponse(
                call: Call<ResponseSignUpDTO>,
                response: Response<ResponseSignUpDTO>
            ) {
                if (response.isSuccessful) {
                    _successSignup.value = true
                    _signupResult.value = response.body()
                } else { // 400, 500 등의 오류
                    _successSignup.value = false
                    _errorMessage.value = response.message()
                }
            }
            // 유효성 검사는 상의한 후 서버에 시켜도 된다.
            override fun onFailure(call: Call<ResponseSignUpDTO>, t: Throwable) { // 서버통신 자체가 실패
                _successSignup.value = false
                _errorMessage.value = t.message
            }
        }
        )
    }
}
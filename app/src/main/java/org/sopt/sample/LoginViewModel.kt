package org.sopt.sample

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.home.HomeActivity
import org.sopt.sample.home.LoginSharedPreferences
import org.sopt.sample.remote.RequestLoginDTO
import org.sopt.sample.remote.ResponseLoginDTO
import org.sopt.sample.remote.ServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    // backing property: 왜 이런식으로 변수를 만드나? 외부 뷰에서 수정할 필요가 없는 변수
    private val _loginResult: MutableLiveData<ResponseLoginDTO> = MutableLiveData()
    // 뷰모델에서만 내부 값을 변경 가능하도록 private설정
    val loginResult: LiveData<ResponseLoginDTO> = _loginResult
    // 이 변수에 접근해서 값을 변경할 수는 없음.
    // 서버통신 값이 오면 loginResult를 선언한다.
    private val loginService = ServicePool.loginService
    private val _successLogin = MutableLiveData<Boolean>()
    val successLogin: LiveData<Boolean> = _successLogin
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun login(email: String, password: String){
        loginService.login(
            RequestLoginDTO(email, password)
        ).enqueue(object: Callback<ResponseLoginDTO> {
            override fun onResponse(
                call: Call<ResponseLoginDTO>,
                response: Response<ResponseLoginDTO>
            ) {
                if (response.isSuccessful) {
                    _successLogin.value = true
                    _loginResult.value = response.body()
                } else { // 서버랑 연결은 되었으나 여러 문제가 발생 (400, 500 등)
                    _successLogin.value = false
                    _errorMessage.value = response.message()
                }
            }
            // 유효성 검사는 상의한 후 서버에 시켜도 된다.
            override fun onFailure(call: Call<ResponseLoginDTO>, t: Throwable) { // 서버통신 자체가 실패
                _successLogin.value = false
                _errorMessage.value = t.message
            }
        }
        )
    }

}
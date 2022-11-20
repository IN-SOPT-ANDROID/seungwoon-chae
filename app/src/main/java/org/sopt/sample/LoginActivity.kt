package org.sopt.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.home.HomeActivity
import org.sopt.sample.home.LoginSharedPreferences
import org.sopt.sample.remote.RequestLoginDTO
import org.sopt.sample.remote.ResponseLoginDTO
import org.sopt.sample.remote.ServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private val loginservice = ServicePool.loginService
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btLogin.setOnClickListener {
            viewModel.login(
                binding.etEmail.text.toString(),
                binding.etPw.text.toString()
            )
        }

        viewModel.successLogin.observe(this){ success ->
            if(success) {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }

        viewModel.errorMessage.observe(this){ it ->
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    // autologinCheck()
        // loginEvent()
        // signupEvent()
    } // 이 activity는 이제 서버통신과 아무련 관련이 없음.

    /*private fun autologinCheck() { // 자동 로그인 기능, sharedPreference에 값이 존재하면 로그인 화면을 뛰어넘어 홈화면으로 진입
        if (!(LoginSharedPreferences.getUserId(this).isNullOrBlank())
            && !(LoginSharedPreferences.getUserPw(this).isNullOrBlank())
        ) {
            Toast.makeText(this.applicationContext, "자동 로그인 되었습니다", Toast.LENGTH_SHORT).show();
            val homeIntent = Intent(this@LoginActivity, HomeActivity::class.java)
            homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // 로그인 성공 시 직전에 발생한 회원가입/로그인 acitivity 삭제처리
            startActivity(homeIntent)
        }
    }

    private fun loginEvent() {
        binding.btLogin.setOnClickListener() {
            loginservice.login(
                RequestLoginDTO(
                    binding.etEmail.text.toString(),
                    binding.etPw.text.toString(),
                )
            ).enqueue(object : Callback<ResponseLoginDTO> {
                override fun onResponse(
                    call: Call<ResponseLoginDTO>,
                    response: Response<ResponseLoginDTO>
                ) {
                    if(response.isSuccessful) { // 서버와 연결된 상태에서 로그인 성공
                        val homeIntent = Intent(this@LoginActivity, HomeActivity::class.java)
                        LoginSharedPreferences.setUserId(this@LoginActivity, binding.etEmail.text.toString())
                        LoginSharedPreferences.setUserPw(this@LoginActivity, binding.etPw.text.toString()) // 자동 로그인 설정
                        homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // 로그인 성공 시 직전에 발생한 회원가입/로그인 acitivity 삭제처리
                        Toast.makeText(this@LoginActivity, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        startActivity(homeIntent)

                        // val result = responese.body();
                    }
                }

                override fun onFailure(call: Call<ResponseLoginDTO>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun signupEvent() { // 회원가입 Activity로 이동
        binding.btSignup.setOnClickListener() {
            val signupIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signupIntent)
        }
    }*/
}
package org.sopt.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
    val loginservice = ServicePool.loginService
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        autologinCheck()
        loginEvent()
        signupEvent()
    }

    private fun autologinCheck() { // 자동 로그인 기능, sharedPreference에 값이 존재하면 로그인 화면을 뛰어넘어 홈화면으로 진입
        if (!(LoginSharedPreferences.getUserId(this).isNullOrBlank())
            && !(LoginSharedPreferences.getUserPw(this).isNullOrBlank())
        ) {
            Toast.makeText(this.applicationContext, "자동 로그인 되었습니다", Toast.LENGTH_SHORT).show();
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
        }
    }

    private fun loginEvent() {
        val homeIntent = Intent(this, HomeActivity::class.java)
        binding.btLogin.setOnClickListener() {
            /*if (binding.etId.text.toString() == id && binding.etPw.text.toString() == pw) { // 로그인 성공 여부를 검증하는 과정 필요
                LoginSharedPreferences.setUserId(this, id.toString())
                LoginSharedPreferences.setUserPw(this, pw.toString())
                Toast.makeText(this.applicationContext, "로그인에 성공했습니다", Toast.LENGTH_SHORT).show();
                val homeIntent = Intent(this, HomeActivity::class.java)
                homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // 로그인 성공 시 직전에 발생한 회원가입/로그인 acitivity 삭제처리
                startActivity(homeIntent)
            } else {
                Toast.makeText(this.applicationContext, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }  */
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
                    Log.e("signing", "error1")
                    if(response.isSuccessful) {
                        // homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // 로그인 성공 시 직전에 발생한 회원가입/로그인 acitivity 삭제처리
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    }
                }

                override fun onFailure(call: Call<ResponseLoginDTO>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
        LoginSharedPreferences.setUserId(this, binding.etEmail.text.toString())
        LoginSharedPreferences.setUserPw(this, binding.etPw.text.toString()) // 자동 로그인 설정
    }

    private fun signupEvent() {
        binding.btSignup.setOnClickListener() {
            val signupIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signupIntent)
        }
    }
}
package org.sopt.sample

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivitySignupBinding
import org.sopt.sample.home.HomeActivity
import org.sopt.sample.home.LoginSharedPreferences
import org.sopt.sample.remote.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    val signUpService = ServicePool.singupService
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickEvent()
    }

    private fun clickEvent() {
        with(binding)
        {
            btSignupend.setOnClickListener() {
                Log.e(binding.etEmailNew.text.toString(), "input")
                Log.e(binding.etIdNew.text.toString(), "input")
                Log.e(binding.etPwNew.text.toString(), "input")
                // Snackbar.make(root, "회원가입이 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
                // 회원가입이 성공한 id와 pw를 서버에 POST로 전달하고 loginActivity로 이동한다.
                signUpService.signup(
                    RequestSignUpDTO(
                        binding.etEmailNew.text.toString(),
                        binding.etPwNew.text.toString(),
                        binding.etIdNew.text.toString()
                    )
                ).enqueue(object : Callback<ResponseSignUpDTO> {
                    override fun onResponse(
                        call: Call<ResponseSignUpDTO>,
                        response: Response<ResponseSignUpDTO>
                    ) {
                        when(response.code()) {
                            400 -> {
                                Log.e("400", "400err")
                                Toast.makeText(this@SignUpActivity, "회원가입 실패: 아이디가 비번이 올바르지 않습니다.", Toast.LENGTH_SHORT)
                            }
                            500 ->  {
                                Log.e("500", "500err")
                                Toast.makeText(this@SignUpActivity, "회원가입 실패: 서버 오류", Toast.LENGTH_SHORT)
                            }
                        }
                            if(response.isSuccessful) {
                                Log.e("clear", "errnot")
                                startActivity(
                                    Intent(
                                        this@SignUpActivity,
                                        LoginActivity::class.java
                                    )
                                )
                            }

                       // startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                    }
                    override fun onFailure(call: Call<ResponseSignUpDTO>, t: Throwable) {
                        Toast.makeText(this@SignUpActivity, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("error", "errro3")
                    }
                })
            }
        }
    }
}


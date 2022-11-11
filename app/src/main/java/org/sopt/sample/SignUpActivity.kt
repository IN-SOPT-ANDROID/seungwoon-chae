package org.sopt.sample

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
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
    private val signUpService = ServicePool.singupService
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btSignupend.isEnabled = false
        checkAllInputActivated()
        clickEvent()
    }

    private fun inputNotEmpty(): Boolean { // 3개의 input의 공백 여부를 판단하는 함수
        with(binding){
            if(!etIdNew.text.toString().isBlank() && !etPwNew.text.toString().isBlank() && !etEmailNew.text.toString().isBlank()){
                return true
            }
            return false
        }
    }

    private fun checkAllInputActivated(){
        with(binding)
        {
            etPwNew.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    btSignupend.isEnabled = inputNotEmpty() // 3개의 input이 모두 공백이 아니라면 버튼 활성화
                }
            })
            etIdNew.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    btSignupend.isEnabled = inputNotEmpty()
                }
            })
            etEmailNew.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    btSignupend.isEnabled = inputNotEmpty()
                }
            })
        }
    }

    private fun clickEvent() {
        with(binding)
        {
            btSignupend.setOnClickListener() {
                // 회원가입이 성공한 id와 pw를 서버에 전달하고 loginActivity로 이동한다.
                signUpService.signup(
                    RequestSignUpDTO(
                        etEmailNew.text.toString(),
                        etPwNew.text.toString(),
                        etIdNew.text.toString()
                    )
                ).enqueue(object : Callback<ResponseSignUpDTO> {
                    override fun onResponse(
                        call: Call<ResponseSignUpDTO>,
                        response: Response<ResponseSignUpDTO>
                    ) {
                        when(response.code()) {
                            400 -> {
                                Toast.makeText(this@SignUpActivity, "회원가입 실패: 아이디와 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT)
                            }
                            500 ->  {
                                Toast.makeText(this@SignUpActivity, "회원가입 실패: 서버 오류", Toast.LENGTH_SHORT)
                            }
                        }
                        if(response.isSuccessful) {
                            startActivity(
                                Intent(
                                    this@SignUpActivity,
                                    LoginActivity::class.java
                                )
                            )
                        }
                    }
                    override fun onFailure(call: Call<ResponseSignUpDTO>, t: Throwable) {
                        Toast.makeText(this@SignUpActivity, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
        }
    }
}


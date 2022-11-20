package org.sopt.sample

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import org.sopt.sample.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivitySignupBinding
import org.sopt.sample.home.HomeActivity
import org.sopt.sample.home.LoginSharedPreferences
import org.sopt.sample.remote.*
import org.sopt.sample.viewmodel.SignupViewModel
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private val viewModel by viewModels<SignupViewModel>()
    private var validEmail: Boolean = false
    private var validPw: Boolean = false
    private lateinit var binding: ActivitySignupBinding
    val Emailpattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,10}$"
    val Pwpattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{6,12}$"

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.btSignupend.isEnabled = false // 조건 충족 시에만 버튼 활성화
        binding.txtPwError.visibility = View.INVISIBLE
        binding.txtEmailError.visibility = View.INVISIBLE

        viewModel.inputEmail.observe(this){ // 형식 검증 후 실패시 et 밑줄 빨강 + 에러메시지 visible
            if(validEmailcheck(it)){
                validEmail = true
                binding.txtEmailError.visibility = View.INVISIBLE
                if(validEmail && validPw){
                    binding.btSignupend.isEnabled = true
                }
            } else {
                validEmail = false
                binding.btSignupend.isEnabled = false
                if(it.isNotBlank()) {
                    binding.txtEmailError.visibility = View.VISIBLE
                }
            }
        }

        viewModel.inputPw.observe(this){
            if(validPwcheck(it)){
                validPw = true
                binding.txtPwError.visibility = View.INVISIBLE
                if(validEmail && validPw){
                    binding.btSignupend.isEnabled = true // 유효성 검사 통과 -> 버튼 활성화
                }
            } else {
                validPw = false
                binding.btSignupend.isEnabled = false
                if(it.isNotBlank()) {
                    binding.txtPwError.visibility = View.VISIBLE
                }
            }
        }

        binding.btSignupend.setOnClickListener {
            viewModel.signup(
                binding.etEmailNew.text.toString(),
                binding.etPwNew.text.toString(),
                binding.etIdNew.text.toString(),
            )
            viewModel.successSignup.observe(this){
                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            }
        }

        viewModel.serverError.observe(this){ it -> // 에러메시지 출력
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    // 3개의 input이 모두 공백아 아닌 것이 확인되면 버튼 활성화
    /*private fun checkAllInputActivated(){
        with(binding)
        {
            etPwNew.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    btSignupend.isEnabled = !etIdNew.text.toString().isBlank() && !etPwNew.text.toString().isBlank() && !etEmailNew.text.toString().isBlank()
                }
            })
            etIdNew.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    btSignupend.isEnabled = !etIdNew.text.toString().isBlank() && !etPwNew.text.toString().isBlank() && !etEmailNew.text.toString().isBlank()
                }
            })
            etEmailNew.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    btSignupend.isEnabled = !etIdNew.text.toString().isBlank() && !etPwNew.text.toString().isBlank() && !etEmailNew.text.toString().isBlank()
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
    }*/

    fun validEmailcheck(Email: String): Boolean {
        val pattern = Pattern.compile(Emailpattern)
        return pattern.matcher(Email).find()
    }

    fun validPwcheck(Pw: String): Boolean {
        val pattern = Pattern.compile(Pwpattern)
        return pattern.matcher(Pw).find()
    }
}


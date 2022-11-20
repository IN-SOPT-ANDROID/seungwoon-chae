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
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        viewModel.inputEmailcheck.observe(this){ // 형식 검증 변수 확인, 실패시 et 밑줄 빨강 + 에러메시지 visible
            if(it){
                binding.txtEmailError.visibility = View.INVISIBLE // 형식 통과
                if(viewModel.inputPwcheck.value == true){ // 비번까지 정상이면 버튼 활성화
                    binding.btSignupend.isEnabled = true
                }
            } else {
                binding.btSignupend.isEnabled = false // 버튼 비활성화
                if(viewModel.inputEmail.value.equals("")){ // 공백이면 오류메시지 출력하지 않음
                    binding.txtEmailError.visibility = View.INVISIBLE
                } else { // 공백이 아니면서 observe 결과가 false면 오류상태
                    binding.txtEmailError.visibility = View.VISIBLE
                }
            }
        }

        viewModel.inputPwcheck.observe(this){
            if(it){
                binding.txtPwError.visibility = View.INVISIBLE // 형식 통과
                if(viewModel.inputEmailcheck.value == true){ // 이메일까지 정상이면 버튼 활성화
                    binding.btSignupend.isEnabled = true
                }
            } else {
                binding.btSignupend.isEnabled = false // 버튼 비활성화
                if(viewModel.inputPw.value.equals("")){// 공백이면 오류메시지 출력하지 않음
                    binding.txtPwError.visibility = View.INVISIBLE
                } else { // 공백이 아니면서 observe 결과가 false면 오류상태
                    binding.txtPwError.visibility = View.VISIBLE
                }
            }
        } // 확장성이 불안해 보이는 코드. mediatorLiveData를 활용할 수는 없을까?

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
}


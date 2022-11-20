package org.sopt.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivitySignupBinding
import org.sopt.sample.viewmodel.SignupViewModel

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
                binding.txtEmailNew.error = ""
                if(viewModel.inputPwcheck.value == true){ // 비번까지 정상이면 버튼 활성화
                    binding.btSignupend.isEnabled = true
                }
            } else {
                binding.btSignupend.isEnabled = false // 버튼 비활성화
                if(viewModel.inputEmail.value.equals("")){ // 공백이면 오류메시지 출력하지 않음
                    binding.txtEmailNew.error = ""
                } else { // 공백이 아니면서 observe 결과가 false면 오류상태
                    binding.txtEmailNew.error = "이메일 형식이 올바르지 않습니다."
                }
            }
        }

        viewModel.inputPwcheck.observe(this){
            if(it){
                Log.d("pw", viewModel.inputPw.value.toString())
                binding.txtPwNew.error = ""
                if(viewModel.inputEmailcheck.value == true){ // 이메일까지 정상이면 버튼 활성화
                    binding.btSignupend.isEnabled = true
                }
            } else {
                binding.btSignupend.isEnabled = false // 버튼 비활성화
                if(viewModel.inputPw.value.equals("")){// 공백이면 오류메시지 출력하지 않음
                    binding.txtPwNew.error = ""
                } else { // 공백이 아니면서 observe 결과가 false면 오류상태
                    binding.txtPwNew.error = "비밀번호 형식이 올바르지 않습니다."
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


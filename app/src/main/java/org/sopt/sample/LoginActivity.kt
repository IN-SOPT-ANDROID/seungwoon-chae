package org.sopt.sample

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.home.HomeActivity
import org.sopt.sample.home.MySharedPreferences


class LoginActivity : AppCompatActivity() {
    private var id: String? = null
    private var pw: String? = null
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        autologinCheck()
        signupCheck()

        binding.btLogin.setOnClickListener() {
            loginEvent()
        }

        binding.btSignup.setOnClickListener() {
            signupEvent()
        }
    }

    private fun autologinCheck() { // 자동 로그인 기능, sharedPreference에 값이 존재하면 로그인 화면을 뛰어넘어 홈화면으로 진입
        if(!(MySharedPreferences.getUserId(this).isNullOrBlank())
            && !(MySharedPreferences.getUserPw(this).isNullOrBlank())){
                Toast.makeText(this.applicationContext, "자동 로그인 되었습니다", Toast.LENGTH_SHORT).show();
                val homeIntent = Intent(this, HomeActivity::class.java)
                homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // 로그인 성공 시 직전에 발생한 회원가입/로그인 acitivity 삭제처리
                startActivity(homeIntent)
            }
    }

    private fun signupCheck() { // 회원가입 activity에서 넘어오는 상황 감지
        if (intent.hasExtra("id") && intent.hasExtra("pw")) {
            Snackbar.make(binding.root, "회원가입이 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
            id = intent.getStringExtra("id")
            pw = intent.getStringExtra("pw")
        }
    }

    private fun loginEvent() {
        if (binding.etId.text.toString() == id && binding.etPw.text.toString() == pw) {
            MySharedPreferences.setUserId(this, id.toString())
            MySharedPreferences.setUserPw(this, pw.toString())
            Toast.makeText(this.applicationContext, "로그인에 성공했습니다", Toast.LENGTH_SHORT).show();
            val homeIntent = Intent(this, HomeActivity::class.java)
            homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // 로그인 성공 시 직전에 발생한 회원가입/로그인 acitivity 삭제처리
            startActivity(homeIntent)
        } else {
            Toast.makeText(this.applicationContext, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private fun signupEvent() {
        val signupIntent = Intent(this, SignUpActivity::class.java)
        startActivity(signupIntent)
    }
}
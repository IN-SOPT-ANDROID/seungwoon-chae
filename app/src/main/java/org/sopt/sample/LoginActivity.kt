package org.sopt.sample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.home.HomeActivity

class LoginActivity : AppCompatActivity() {
    private var id: String? = null
    private var pw: String? = null
    private var mbti: String? = null
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        signupCheck()

        binding.btLogin.setOnClickListener() {
            loginEvent()
        }

        binding.btSignup.setOnClickListener() {
            signupEvent()
        }
    }

    private fun signupCheck() {
        if (intent.hasExtra("id") && intent.hasExtra("pw")) {
            Snackbar.make(binding.root, "회원가입이 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
            id = intent.getStringExtra("id")
            pw = intent.getStringExtra("pw")
            mbti = intent.getStringExtra("mbti")
        }
    }

    private fun loginEvent() {
        if (binding.etId.text.toString() == id && binding.etPw.text.toString() == pw) {
            Toast.makeText(this.applicationContext, "로그인에 성공했습니다", Toast.LENGTH_SHORT).show();
            val homeIntent = Intent(this, HomeActivity::class.java)
            homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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
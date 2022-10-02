package org.sopt.sample

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivitySignupBinding

class SignUpActivity: AppCompatActivity(){
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btSignupend.setOnClickListener() {
            if (binding.etId.text.length >= 6 && binding.etId.text.length <= 10) {
                if (binding.etPw.text.length >= 8 && binding.etPw.text.length <= 12) {
                    Snackbar.make(binding.root, "회원가입이 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("id", binding.etId.text.toString())
                    intent.putExtra("pw", binding.etPw.text.toString())
                    intent.putExtra("mbti", binding.etMbti.text.toString())
                    startActivity(intent)
                } else {
                    Snackbar.make(binding.root, "비밀번호는 8~12글자 이내입니다.", Snackbar.LENGTH_SHORT).show()
                }
            } else{
                Snackbar.make(binding.root, "ID는 6~10글자 이내입니다.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}


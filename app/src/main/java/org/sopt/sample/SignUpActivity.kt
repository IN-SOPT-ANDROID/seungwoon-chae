package org.sopt.sample

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, LoginActivity::class.java)
        clickEvent(intent)
    }

    private fun clickEvent(intent: Intent) {
        with(binding)
        {
            btSignupend.setOnClickListener() {
                if (etId.text.length >= 6 && etId.text.length <= 10) {
                    if (etPw.text.length >= 8 && etPw.text.length <= 12) {
                        Snackbar.make(root, "회원가입이 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
                        intent.putExtra("id", etId.text.toString())
                        intent.putExtra("pw", etPw.text.toString())
                        intent.putExtra("mbti", etMbti.text.toString())
                        startActivity(intent)
                    } else {
                        Snackbar.make(root, "비밀번호는 8~12글자 이내입니다.", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Snackbar.make(root, "ID는 6~10글자 이내입니다.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}


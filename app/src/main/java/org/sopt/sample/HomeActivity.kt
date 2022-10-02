package org.sopt.sample

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.databinding.ActivityHomeBinding
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivityLoginBinding

class HomeActivity: AppCompatActivity(){
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra("homeid") && intent.hasExtra("homembti")) {
            binding.txtIntroname.setText("이름: " + intent.getStringExtra("homeid"))
            binding.txtIntrombti.setText("MBTI: " + intent.getStringExtra("homembti"))
        }
    }
}

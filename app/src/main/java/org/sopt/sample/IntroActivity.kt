package org.sopt.sample

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.databinding.ActivityHomeBinding
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivityIntroBinding
import org.sopt.sample.databinding.ActivityLoginBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showInfo(intent)
    }

    private fun showInfo(intent: Intent) {
        if (intent.hasExtra("homeid") && intent.hasExtra("homembti")) {
            binding.txtIntroname.setText(getString(R.string.NAMETITLE) + intent.getStringExtra("homeid"))
            binding.txtIntrombti.setText(getString(R.string.MBTITITLE) + intent.getStringExtra("homembti"))
        }
    }
}
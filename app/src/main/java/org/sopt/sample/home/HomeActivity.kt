package org.sopt.sample.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.databinding.ActivityHomeBinding
import org.sopt.sample.R

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeFragment = HomeFragment()
    private val galleryFragment = MyPageFragment()
    private val searchFragment = SearchFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction() // 최초 액티비티에 등장할 fragment는 homeFragment
            .replace(R.id.home_container, homeFragment)
            .commit()



        binding.bnvHome.setOnItemSelectedListener { // fragment 전환을 위한 이벤트리스너
            when (it.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.home_container, homeFragment)
                        .commit()
                }
                R.id.nav_gallery -> {
                    supportFragmentManager.beginTransaction().replace(R.id.home_container, galleryFragment).commit()
                }
                R.id.nav_search -> {
                    supportFragmentManager.beginTransaction().replace(R.id.home_container, searchFragment).commit()
                }
            }
            true
        }

        binding.bnvHome.setOnItemReselectedListener { // 다시 아이템이 선택될 때 발생하는 이벤트리스너
            when (it.itemId) {
                R.id.nav_home -> {
                    homeFragment.viewToFirst()
                }
            }
            true
        }
    }
}

package org.sopt.sample.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.adapters.AdapterViewBindingAdapter.setOnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.sopt.sample.databinding.ActivityHomeBinding
import org.sopt.sample.R

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeFragment = HomeFragment()
    // private val galleryFragment = GalleryFragment()
    // private val searchFragment = SearchFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.home_container, homeFragment)
            .commit()

        binding.bnvHome.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.home_container, homeFragment)
                        .commit()
                }
                /*R.id.nav_gallery -> {
                    supportFragmentManager.beginTransaction().replace(, galleryFragment).commit()
                }
                R.id.nav_search -> {
                    supportFragmentManager.beginTransaction().replace(R.id.layout_nav_bottom, searchFragment).commit()
                }*/
            }
            true
        }

        binding.bnvHome.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    homeFragment.viewToFirst()
                    // 리사이클러 뷰를 최상단으로 끌어올려야함.
                }
            }
            true
        }
    }
}

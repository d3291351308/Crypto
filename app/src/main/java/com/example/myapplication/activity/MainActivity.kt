package com.example.myapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.MainActivityBinding
import com.example.myapplication.fragment.EmptyFragment
import com.example.myapplication.fragment.WalletFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置底部导航
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navView: BottomNavigationView = binding.bottomNavigation

         navView.setOnNavigationItemSelectedListener { item ->
             when (item.itemId) {
                 R.id.navigation_wallet -> {
                     supportFragmentManager.beginTransaction()
                         .replace(R.id.fragment_container, WalletFragment())
                         .commit()
                     true
                 }
                 R.id.navigation_market -> {
                     supportFragmentManager.beginTransaction()
                         .replace(R.id.fragment_container, EmptyFragment())
                         .commit()
                     true
                 }
                 else -> false
             }
         }
    }
}
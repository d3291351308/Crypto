package com.example.myapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.MainActivityBinding
import com.example.myapplication.fragment.EmptyFragment
import com.example.myapplication.fragment.WalletFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private var walletFragment: Fragment? = null
    private var emptyFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置底部导航
        setupBottomNavigation(savedInstanceState)
    }

    private fun setupBottomNavigation(savedInstanceState: Bundle?) {
        val navView: BottomNavigationView = binding.bottomNavigation

        if (savedInstanceState == null){
            selectFirstFragment()
        }
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_wallet -> {
                    selectFirstFragment()
                    true
                }

                R.id.navigation_market -> {
                    selectSecFragment()
                    true
                }

                else -> false
            }
        }
    }

    private fun selectFirstFragment() {
        if (walletFragment == null) {
            walletFragment = WalletFragment()
        }
        walletFragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, it).commit()
        }
    }

    private fun selectSecFragment() {
        if (emptyFragment == null) {
            emptyFragment = EmptyFragment()
        }
        emptyFragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, it).commit()
        }
    }
}
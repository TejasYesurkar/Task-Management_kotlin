package com.project.its_show_time

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavaigation : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navaigation)
        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.home -> {
//                    loadFragment(HomeFragment())
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(0,0);
                    return@setOnNavigationItemReselectedListener
                }
                R.id.message -> {
//                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    loadFragment(ChatFragment())
                    return@setOnNavigationItemReselectedListener
                }
                R.id.settings -> {
                    loadFragment(SettingFragment())
                    return@setOnNavigationItemReselectedListener
                }
            }
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun clickAdapater(url:String) {
        var intent:Intent
        intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)

        startActivity(intent)
    }
}
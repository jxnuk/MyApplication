package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.ui.booking.BookingFragment
import com.example.myapplication.ui.community.CommunityFragment
import com.example.myapplication.ui.home.HomeFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start on Home
        replaceFragment(HomeFragment(), "Home")

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment(), "Home")
                R.id.nav_community -> replaceFragment(CommunityFragment(), "Community")
                R.id.nav_booking -> replaceFragment(BookingFragment(), "Bookings")
                else -> true
            }
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> {
                    Toast.makeText(this, "Search tapped", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_profile -> {
                    Toast.makeText(this, "Profile tapped", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, title: String): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHost, fragment)
            .commit()
        findViewById<MaterialToolbar>(R.id.topAppBar).title = "FOODIES"
        // If you prefer dynamic subtitle:
        // findViewById<MaterialToolbar>(R.id.topAppBar).subtitle = title
        return true
    }
}

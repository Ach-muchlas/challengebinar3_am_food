package com.am.amfood.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.am.amfood.R
import com.am.amfood.databinding.ActivityAuthBinding
import com.am.amfood.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpDisplay()
    }

    private fun setUpDisplay() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val adapter = ViewPagerAdapter(fragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        binding.apply {
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.login)))
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.regis)))

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewPager.setCurrentItem(tab.position, true)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
        }

    }

    companion object {
        const val TAG = "LoginActivity"
    }
}

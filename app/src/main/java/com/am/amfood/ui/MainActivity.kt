package com.am.amfood.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.am.amfood.R
import com.am.amfood.adapter.ListCardAdapter
import com.am.amfood.databinding.ActivityMainBinding
import com.am.amfood.model.dummyDataCard

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ListCardAdapter(this, dummyDataCard)
        binding.apply {
            rvCardItem.layoutManager =
                GridLayoutManager(this@MainActivity, 2)
            rvCardItem.adapter = adapter
        }

        binding.cardProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuHome -> {
                    Toast.makeText(this, "Ini sudah di Home Activity", Toast.LENGTH_SHORT).show()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menuLike -> {
                    Toast.makeText(this, "Belom Ada Activity", Toast.LENGTH_SHORT).show()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menuMessage -> {
                    Toast.makeText(this, "Belom Ada Activity", Toast.LENGTH_SHORT).show()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menuProfile -> {
                    val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }
}
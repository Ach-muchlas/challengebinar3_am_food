package com.am.amfood.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
        binding.cardShop.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}
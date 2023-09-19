package com.am.amfood.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.am.amfood.databinding.ActivityDetailBinding
import com.am.amfood.model.CardModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataName = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<CardModel>(EXTRA_NAME, CardModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<CardModel>(EXTRA_NAME)
        }

        binding.textViewNameItem.text = dataName?.name


        binding.cardBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    companion object{
        const val EXTRA_NAME = "extra_name"
    }
}
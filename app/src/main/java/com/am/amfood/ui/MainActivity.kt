package com.am.amfood.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.am.amfood.R
import com.am.amfood.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var binding2 : ContainerItemBinding
//    private var isClicked = false

    //    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        binding2 = ContainerItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigation

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)


//        val adapter = ListCardAdapter(this, dummyDataCard)
//
//        binding.apply {
//            /*setUp default layout manager*/
//            rvCardItem.layoutManager =
//                GridLayoutManager(this@MainActivity, 2)
//            rvCardItem.adapter = adapter
//
//            /*set up linear or grid layout manager*/
//            iconGridOrList.setOnClickListener {
//                if (isClicked) {
//                    rvCardItem.layoutManager = LinearLayoutManager(this@MainActivity)
//                    iconGridOrList.setImageDrawable(getDrawable(R.drawable.list))
//                } else {
//                    rvCardItem.layoutManager = GridLayoutManager(this@MainActivity, 2)
//                    iconGridOrList.setImageDrawable(getDrawable(R.drawable.more))
//                }
//                isClicked = !isClicked
//            }
//
//            /*navigate to profile*/
//            cardProfile.setOnClickListener {
//                startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
//            }
//
//            /*Bottom Navigation*/
//            bottomNavigation.setOnNavigationItemSelectedListener { item ->
//                when (item.itemId) {
//                    R.id.menuHome -> {
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Ini sudah di Home Activity",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        return@setOnNavigationItemSelectedListener true
//                    }
//
//                    R.id.menuLike -> {
//                        Toast.makeText(this@MainActivity, "Belom Ada Activity", Toast.LENGTH_SHORT)
//                            .show()
//                        return@setOnNavigationItemSelectedListener true
//                    }
//
//                    R.id.menuMessage -> {
//                        Toast.makeText(this@MainActivity, "Belom Ada Activity", Toast.LENGTH_SHORT)
//                            .show()
//                        return@setOnNavigationItemSelectedListener true
//                    }
//
//                    R.id.menuProfile -> {
//                        val intent = Intent(this@MainActivity, ProfileActivity::class.java)
//                        startActivity(intent)
//                        return@setOnNavigationItemSelectedListener true
//                    }
//
//                    else -> return@setOnNavigationItemSelectedListener false
//                }
//            }
//        }
//
//        binding2.apply {
//            cardLike.setOnClickListener {
//                if(isClicked){
//                    like.setImageDrawable(getDrawable(R.drawable.love))
//                }else{
//                    like.setImageDrawable(getDrawable(R.drawable.hati))
//                }
//                isClicked = !isClicked
//            }
//        }
    }
}
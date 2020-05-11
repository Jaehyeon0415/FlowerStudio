package com.jaehyeon.flowerstudio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_camera,
            R.id.navigation_dictionary,
            R.id.navigation_profile))

        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        navView.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.navigation_camera -> {
//                    startActivity(Intent(this@MainActivity, CameraActivity::class.java))
//                }
//            }
//            false
//        }
    }
}

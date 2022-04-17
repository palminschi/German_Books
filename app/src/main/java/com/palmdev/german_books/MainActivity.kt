package com.palmdev.german_books

import android.os.Bundle
import android.view.ActionMode
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.palmdev.german_books.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Init navigation
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)

    }




    companion object{
        lateinit var bottomNavigationView: BottomNavigationView

        fun hideBottomNavigation(){
            bottomNavigationView.visibility = View.GONE
        }
        fun showBottomNavigation(){
            bottomNavigationView.visibility = View.VISIBLE
        }
    }
}
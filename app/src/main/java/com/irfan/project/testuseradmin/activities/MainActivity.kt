package com.irfan.project.testuseradmin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.irfan.project.testuseradmin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navigationController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationController = findNavController(R.id.navhomeFragment)
        /**
         * set up navigation controller to bootmbar
         */
        botomNav.setupWithNavController(navigationController)

        NavigationUI.setupActionBarWithNavController(this, navigationController)

    }

        override fun onSupportNavigateUp() = navigationController.navigateUp()
}

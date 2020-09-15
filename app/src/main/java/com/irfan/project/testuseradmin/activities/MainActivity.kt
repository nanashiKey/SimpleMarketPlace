package com.irfan.project.testuseradmin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.irfan.project.testuseradmin.R
import com.irfan.project.testuseradmin.helpers.MethodHelpers
import com.irfan.project.testuseradmin.helpers.PrefsHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*

class MainActivity : AppCompatActivity() {
    private lateinit var navigationController : NavController
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationController = findNavController(R.id.navhomeFragment)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        /**
         * set up navigation controller to bootmbar
         */
        MethodHelpers.setWindowsBar(this@MainActivity, R.color.colorPrimaryDark)
        NavigationUI.setupActionBarWithNavController(this, navigationController)
        botomNav.setupWithNavController(navigationController)

        val username = PrefsHelper(this@MainActivity).getUserName()
        val userEmail = PrefsHelper(this@MainActivity).getUserEmail()
        val userPoint = PrefsHelper(this@MainActivity).getUserPoint()

        tvUsername.text = "$username \n$userEmail"
        tvItemPoint.text = "Point : ${userPoint}"

    }

        override fun onSupportNavigateUp() = navigationController.navigateUp()
}

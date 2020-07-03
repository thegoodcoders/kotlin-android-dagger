package com.androidavanzado.popcorn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.androidavanzado.popcorn.common.Constants
import com.androidavanzado.popcorn.common.MyApp
import com.androidavanzado.popcorn.ui.people_detail.PersonDetailScrollingActivity
import com.androidavanzado.popcorn.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {

    lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_movies, R.id.navigation_people))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        Log.i("SHAREDVM", "sharedViewModel en MainActivity: $sharedViewModel")

        navPersonDetail()

    }

    fun navPersonDetail() {
        sharedViewModel.personSelected.observe(this, Observer{
            val intent = Intent(MyApp.instance, PersonDetailScrollingActivity::class.java).apply {
                putExtra(Constants.EXTRA_PERSON_ID, it.id)
                putExtra(Constants.EXTRA_PERSON_PHOTO, it.profile_path)
                putExtra(Constants.EXTRA_PERSON_NAME, it.name)
            }
            startActivity(intent)
        })

    }
}

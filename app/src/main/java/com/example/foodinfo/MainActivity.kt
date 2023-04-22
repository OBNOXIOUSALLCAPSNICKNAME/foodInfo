package com.example.foodinfo

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.foodinfo.databinding.ActivityMainBinding


class MainActivity : com.example.foodinfo.core.ui.base.BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment) {
            NavigationUI.setupWithNavController(binding.navView, navController)
            navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
                when (destination.id) {
                    R.id.f_home,
                    R.id.f_favorite,
                    R.id.f_planner,
                    R.id.f_settings -> binding.navView.isVisible = true
                    else            -> binding.navView.isVisible = false
                }
            }
        }
    }
}
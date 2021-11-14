package org.sehproject.sss.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.sehproject.sss.R
import org.sehproject.sss.UserInfo
// import org.sehproject.sss.databinding.ActivityMainBinding
import org.sehproject.sss.datatype.User

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)
        Log.d("tag", "...${UserInfo.userName}")

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.loginFragment) as NavHostFragment? ?: return
        val navController = host.navController
        setupBottomNavMenu(navController)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.loginFragment || destination.id == R.id.registerFragment) {
                bottomNav.visibility = View.GONE
            } else {
                bottomNav.visibility = View.VISIBLE
            }
        }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav?.setupWithNavController(navController)
    }
}
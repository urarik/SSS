package org.sehproject.sss.view

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import org.sehproject.sss.R
import org.sehproject.sss.UserInfo
// import org.sehproject.sss.databinding.ActivityMainBinding
import org.sehproject.sss.datatype.User

class MainActivity : AppCompatActivity() {
    private val PERMISSION = 3333
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)
        Log.d("tag", "...${UserInfo.userName}")

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.loginFragment) as NavHostFragment? ?: return
        val navController = host.navController
        setupBottomNavMenu(navController)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == -1) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            PERMISSION)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.registerFragment) {
                bottomNav.visibility = View.GONE
            } else {
                bottomNav.visibility = View.VISIBLE
            }
        }
        sendToken()
    }

    private fun sendToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(!task.isSuccessful) {
                Log.d("TAG", "Fetching FCM registration token failed ${task.exception}")
            }

            val token = task.result
            val msg = token.toString()
            Log.d("TAG", msg)
            //TODO("토큰을 서버에 보내야 한다.")
            //로그인할 때 토큰을 보낸다.
        }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav?.setupWithNavController(navController)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        Log.d("TAG", "onDestroyed!!")
        super.onDestroy()
    }
}
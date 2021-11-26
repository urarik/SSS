package org.sehproject.sss.view

import android.Manifest
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.sehproject.sss.InvitationDialogFragment
import org.sehproject.sss.R
import org.sehproject.sss.UserInfo
import org.sehproject.sss.datatype.Invitation
// import org.sehproject.sss.databinding.ActivityMainBinding
import org.sehproject.sss.datatype.User
import org.sehproject.sss.utils.NavigationListener
import org.sehproject.sss.utils.ViewPagerAdapter
import org.sehproject.sss.utils.ViewPagerPageChangeCallback

class MainActivity : AppCompatActivity() {
    private val PERMISSION = 3333
    private val sfm = supportFragmentManager
    private lateinit var mOnItemSelectedListener: NavigationBarView.OnItemSelectedListener
    private val mMessageReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent!!.extras
            processNotification(bundle!!)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)

        val bundle = intent.extras
        if(bundle != null) {
            processNotification(bundle)
        }
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager)
        mOnItemSelectedListener = NavigationListener(viewPager2)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener(mOnItemSelectedListener)

        val viewPagerAdapter = ViewPagerAdapter(this, 5)
        viewPager2.adapter = viewPagerAdapter
        viewPager2.registerOnPageChangeCallback(ViewPagerPageChangeCallback(bottomNav))

//        val navHostFragment =
//            sfm.findFragmentById(R.id.nav_container) as NavHostFragment
//        val navController = navHostFragment.navController
//        bottomNav.setupWithNavController(navController)
//
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == -1) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
//            PERMISSION)
//        }
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            if(destination.id == R.id.loginFragment || destination.id == R.id.registerFragment) {
//                bottomNav.visibility = View.GONE
//            } else {
//                bottomNav.visibility = View.VISIBLE
//            }
//        }
        sendToken()
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver), IntentFilter("Invitation"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

    private fun sendToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(!task.isSuccessful) {
                Log.d("TAG", "Fetching FCM registration token failed ${task.exception}")
            }

            val token = task.result
            val msg = token.toString()
            Log.d("TAG", "token: $msg")
            //TODO("토큰을 서버에 보내야 한다.")
            //로그인할 때 토큰을 보낸다.
        }
    }

/*        bottomNav.setOnItemSelectedListener { item ->
            Log.d("TAG", sfm.backStackEntryCount.toString())
            navController.navigate(item.itemId)
            sfm.popBackStackImmediate("약속 목록", 0)
            true
        }
    }*/

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super<AppCompatActivity>.onDestroy()
    }

    private fun processNotification(bundle: Bundle) {
        val bundleSent = bundleOf()
        bundleSent.putString("invite_type", bundle.get("invite_type").toString())
        bundleSent.putString("target_name", bundle.get("target_name").toString())
        bundleSent.putString("inviter", bundle.get("inviter").toString())

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<InvitationDialogFragment>(R.id.view_pager, args = bundle)
        }
    }


}
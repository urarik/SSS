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
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
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
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver), IntentFilter("Invitation"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

/*        bottomNav.setOnItemSelectedListener { item ->
            Log.d("TAG", sfm.backStackEntryCount.toString())
            navController.navigate(item.itemId)
            sfm.popBackStackImmediate("약속 목록", 0)
            true
        }
    }*/

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
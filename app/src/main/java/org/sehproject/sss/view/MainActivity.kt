package org.sehproject.sss.view

// import org.sehproject.sss.databinding.ActivityMainBinding
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import org.sehproject.sss.InvitationDialogFragment
import org.sehproject.sss.R
import org.sehproject.sss.utils.NavigationListener
import org.sehproject.sss.utils.ViewPagerAdapter
import org.sehproject.sss.utils.ViewPagerPageChangeCallback

class MainActivity : AppCompatActivity() {
    private lateinit var mOnItemSelectedListener: NavigationBarView.OnItemSelectedListener
    private val mMessageReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent!!.extras
            processNotification(bundle!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        //reduce viewpager2 drag sensitivity
        val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true
        val recyclerView = recyclerViewField.get(viewPager2) as RecyclerView

        val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopField.isAccessible = true
        val touchSlop = touchSlopField.get(recyclerView) as Int
        touchSlopField.set(recyclerView, touchSlop*8)       // "8" was obtained experimentally
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver), IntentFilter("Invitation"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

    private fun processNotification(bundle: Bundle) {
        val invitationDialogFragment = InvitationDialogFragment()

        invitationDialogFragment.arguments = bundle
        invitationDialogFragment.show(supportFragmentManager, "msg")
    }
}
package org.sehproject.sss.utils

import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import org.sehproject.sss.R

class NavigationListener(private val viewPager2: ViewPager2): NavigationBarView.OnItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
       return when(item.itemId) {
           R.id.planListFragment -> {
               viewPager2.currentItem = 0
               true
           }
           R.id.recommendFragment -> {
               viewPager2.currentItem = 1
               true
           }
           R.id.groupListFragment -> {
               viewPager2.currentItem = 2
               true
           }
           R.id.friendListFragment -> {
               viewPager2.currentItem = 3
               true
           }
           R.id.profileFragment -> {
               viewPager2.currentItem = 4
               true
           }
           else -> false
       }
    }
}
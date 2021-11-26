package org.sehproject.sss.utils

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import org.sehproject.sss.R

class ViewPagerPageChangeCallback(private val bottomNav: BottomNavigationView): ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        bottomNav.selectedItemId = when (position) {
            0 -> R.id.planListFragment
            1 -> R.id.recommendFragment
            2 -> R.id.groupListFragment
            3 -> R.id.friendListFragment
            4 -> R.id.profileFragment
            else -> bottomNav.selectedItemId
        }
    }
}
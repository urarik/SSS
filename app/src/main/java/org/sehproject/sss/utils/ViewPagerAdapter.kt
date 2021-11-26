package org.sehproject.sss.utils

import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.sehproject.sss.ProfileFragment
import org.sehproject.sss.view.PlanContainerFragment
import org.sehproject.sss.view.container.GroupContainerFragment
import org.sehproject.sss.view.RecommendFragment
import org.sehproject.sss.view.container.FriendContainerFragment
import org.sehproject.sss.view.container.ProfileContainerFragment

class ViewPagerAdapter(fa: FragmentActivity, private val count: Int) : FragmentStateAdapter(fa){

    init {
        // Add a FragmentTransactionCallback to handle changing
        // the primary navigation fragment
        registerFragmentTransactionCallback(object : FragmentTransactionCallback() {
            override fun onFragmentMaxLifecyclePreUpdated(
                fragment: Fragment,
                maxLifecycleState: Lifecycle.State
            ) = if (maxLifecycleState == Lifecycle.State.RESUMED) {
                // This fragment is becoming the active Fragment - set it to
                // the primary navigation fragment in the OnPostEventListener
                OnPostEventListener {
                    fragment.parentFragmentManager.commitNow {
                        setPrimaryNavigationFragment(fragment)
                    }
                }
            } else {
                super.onFragmentMaxLifecyclePreUpdated(fragment, maxLifecycleState)
            }
        })

    }
    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PlanContainerFragment()
            1 -> RecommendFragment()
            2 -> GroupContainerFragment()
            3 -> FriendContainerFragment()
            4 -> ProfileContainerFragment()
            else -> Fragment()
        }
    }
}
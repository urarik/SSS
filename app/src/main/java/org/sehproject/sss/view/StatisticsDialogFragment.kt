package org.sehproject.sss.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentStatisticsPointBinding
import org.sehproject.sss.viewmodel.GroupViewModel
import org.sehproject.sss.viewmodel.ProfileViewModel

class StatisticsDialogFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel: ProfileViewModel by lazy {
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        }
        val statisticsBinding: FragmentStatisticsPointBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_statistics_point, container, false)
        val view = statisticsBinding.root

        /*
        val recyclerView: RecyclerView = statisticsBinding.
        groupViewModel?.setFriendList()
        groupViewModel?.friendListLiveData?.observe(viewLifecycleOwner, Observer {
            adapter = UserAdapter(it)
            recyclerView.adapter = adapter
        })
        profileViewModel?.setFriendList()
         */

        return view
    }
}
package org.sehproject.sss.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import org.sehproject.sss.R
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.FragmentMapBinding


class FriendProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val friendProfileBinding: FragmentFriendProfileBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_friend_profile,
            container,
            false
        )

        return friendProfileBinding.root
    }

}
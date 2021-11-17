package org.sehproject.sss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.FragmentProfileEditBinding

class ProfileEditFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profileEditBinding: FragmentProfileEditBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_profile_edit,
            container,
            false
        )

        initObserver(profileEditBinding.root)
        return profileEditBinding.root
    }

    private fun initObserver(view: View) {

        val button = view.findViewById<Button>(R.id.buttonSaveProfile)
        button?.setOnClickListener {
            findNavController().navigate(R.id.action_profileEditFragment_to_profileFragment, null)
        }
    }

}
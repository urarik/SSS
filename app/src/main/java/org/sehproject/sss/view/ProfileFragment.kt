package org.sehproject.sss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.sehproject.sss.databinding.FragmentFriendProfileBinding
import org.sehproject.sss.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profileBinding: FragmentProfileBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_profile,
            container,
            false
        )

        initObserver(profileBinding.root)
        return profileBinding.root
    }

    private fun initObserver(view: View) {

        val buttonEdit = view.findViewById<Button>(R.id.buttonMyPageEditProfile)
        buttonEdit?.setOnClickListener {
            findNavController().navigate(R.id.profileEditFragment, null)
        }

        val buttonSetting = view.findViewById<Button>(R.id.buttonSetting)
        buttonSetting?.setOnClickListener {
            findNavController().navigate(R.id.settingFragment, null)
        }
    }
}
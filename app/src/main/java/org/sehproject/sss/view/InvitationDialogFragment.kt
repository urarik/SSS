package org.sehproject.sss

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import org.sehproject.sss.datatype.Invitation
import org.sehproject.sss.view.GroupInviteDialogFragmentArgs
import org.sehproject.sss.view.MainActivity
import org.sehproject.sss.viewmodel.FriendViewModel

class InvitationDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val intent = Intent(requireActivity().applicationContext, MainActivity::class.java)
        val invitation = Invitation(requireArguments().getString("invite_type")!!,
            requireArguments().getString("inviter")!!,
            requireArguments().getString("target_name")!!)

        Log.d("TAG", invitation.toString())

        return inflater.inflate(R.layout.fragment_invitation, container, false)
    }



    fun bundle2string(bundle: Bundle?): String? {
        if (bundle == null) {
            return null
        }
        var string = "Bundle{"
        for (key in bundle.keySet()) {
            string += " " + key + " => " + bundle[key] + ";"
        }
        string += " }Bundle"
        return string
    }

}